package com.business.impl;

import cn.hutool.core.date.DateUtil;
import com.annotation.PermissionChecker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bean.CouponCacheBuilder;
import com.business.CorporationBusiness;
import com.business.CouponsBatchBussiness;
import com.business.CouponsBusiness;
import com.dto.CouponCorpDTO;
import com.dto.CouponIndividualDTO;
import com.dto.CouponsBatchDTO;
import com.entity.Coupons;
import com.entity.CouponsBatch;
import com.entity.User;
import com.enums.Role;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.google.gson.Gson;
import com.interceptor.CachePrepareServiceImpl;
import com.interceptor.impl.RedisRateLimitImpl;
import com.service.ICorporationService;
import com.service.ICouponsBatchService;
import com.service.ICouponsService;
import com.service.UserService;
import com.utils.cache.IGlobalCache;
import com.utils.cache.TypeInfo;
import com.vo.CouponWithOutIdVO;
import com.vo.CouponsBatchVO;
import com.vo.CouponsVO;
import com.vo.SingleCouponVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Component
@Slf4j
public class CouponsBusinessImpl implements CouponsBusiness {
    @Autowired
    private UserService userService;
    @Autowired
    private ICorporationService corporationService;
    @Autowired
    private ICouponsBatchService couponsBatchService;
    @Autowired
    private CorporationBusiness corporationBusiness;
    @Autowired
    private ICouponsService couponsService;
    @Autowired
    private CouponsBatchBussiness couponsBatchBussiness;
    @Autowired
    private TransactionTemplate transactionManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DefaultRedisScript<String> redisScript2;
    @Autowired
    private IGlobalCache globalCache;
    @Autowired
    private CouponCacheBuilder couponCacheBuilder;
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    private CachePrepareServiceImpl cachePrepareService;
    @Autowired
    private RedisRateLimitImpl redisRateLimitImpl;
    private static final String TOPIC_NAME = "coupon.issued";
    private static final String COUPON_CACHE = "coupon:stock:";
    private static final String ISMEMBERKEY = "userid:batchid:ismembercheck";
    private static final String LIMIT = "1000";
    // 1000 MS
    private static final Integer WINDOW_TIME = 1000;
    private static final Integer TOTAL_SEC_PER_DAY = 86400;

    @Override
    @Transactional
    public List<Coupons> issueCouponsToCorporation(@NotNull CouponCorpDTO couponCorpDTO) {
        // create a new batch of coupons
        CouponsBatchDTO couponsBatchDTO = setCouponsBatchDTO(couponCorpDTO);
        CouponsBatch couponsBatch = couponsBatchBussiness.createCouponsBatch(couponsBatchDTO);
        Long batchId = couponsBatch.getBatchId();

        // list all the employee/user of the corporation
        List<User> lis = corporationBusiness.getEmployeeList(couponCorpDTO.getCompanyName());
        if (lis == null || lis.isEmpty()) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR);
        }
        List<Coupons> res = new ArrayList<>();
        lis.forEach(user -> {
            // corporation only has one kind of coupon at a time
            Coupons co = couponsService.getOne(new LambdaQueryWrapper<Coupons>().eq(Coupons::getUserId, user.getId()));
            // create new coupon
            if (co == null) {
                Coupons coupons = setNewCoupon(user, batchId);
                couponsService.save(coupons);
                res.add(coupons);
            } else {
                Long couponId = co.getCouponId();
                co.setBatchId(batchId);
                couponsService.update(new LambdaUpdateWrapper<Coupons>().set(Coupons::getBatchId, batchId).eq(Coupons::getCouponId, couponId));
                res.add(co);
            }
        });
        return res;
    }

    @Override
    public CouponWithOutIdVO issueCouponsToIndividual(CouponIndividualDTO couponIndividualDTO) {
        // check params, rate, duplicate
        checkParameters(couponIndividualDTO);
        if (!redisRateLimitImpl.limit(LIMIT, WINDOW_TIME)) {
            throw GeneralExceptionFactory.create(ErrorCode.RATE_LIMIT_ERROR, "too many requests");
        }
        Long batchId = couponIndividualDTO.getBatchId();
        checkIfDuplicateUserAndBatchId(couponIndividualDTO.getUserId(), batchId);

        // stock = stock - 1 if stock > 0 in redis
        String key = COUPON_CACHE + batchId.toString();
        String res = stringRedisTemplate.execute(redisScript2, Arrays.asList(key), String.valueOf(1));
        if (res.equals("-500")){
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "please such coupon");
        } else if (res.equals("-100")) {
            couponsBatchService.update(new LambdaUpdateWrapper<CouponsBatch>().set(CouponsBatch::getStock, 0).eq(CouponsBatch::getBatchId, batchId));
            globalCache.del(key);
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "no more coupons");
        }

        Coupons co  = setNewIndividualCoupon(couponIndividualDTO);
        sendToMQ(co);
        return convertToCouponWithOutIdVO(co);
    }

    public static CouponWithOutIdVO convertToCouponWithOutIdVO(Coupons item) {
        CouponWithOutIdVO result = new CouponWithOutIdVO();
        result.setValidFrom(item.getValidFrom());
        result.setValidTo(item.getValidTo());
        result.setBatchId(item.getBatchId());
        result.setUserId(item.getUserId());
        result.setIsUsed(item.getIsUsed());
        return result;
    }


    @Override
    public CouponsVO getValidCouponsByUserId(Long userId) {
        // check userId role type
        User user = userService.getById(userId);
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "no such userId");
        }
        if (user.getRoleType().equals(TypeInfo.getIndividualRoleType()) && user.getRoleType().equals(TypeInfo.getCorporationRoleType())) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no available user");
        }
        CouponsVO couponsVO = new CouponsVO();
        List<SingleCouponVO> res = new ArrayList<>();
        List<Coupons> couponsList = couponsService.list(new LambdaQueryWrapper<Coupons>().eq(Coupons::getUserId, userId).eq(Coupons::getIsUsed, false));
        couponsVO.setCouponsList(res);
        if (couponsList == null || couponsList.isEmpty()) {
            return couponsVO;
        }
        couponsList.forEach(co -> {
            if (user.getRoleType().equals(TypeInfo.getIndividualRoleType())) {
                if (!Objects.isNull(co.getValidFrom()) && !Objects.isNull(co.getValidTo()) &&  co.getValidTo().after(new Timestamp(System.currentTimeMillis()))) {
                    CouponsBatch couponsBatch = couponsBatchService.getById(co.getBatchId());
                    SingleCouponVO single = getSingleCouponVO(couponsBatch, co);
                    res.add(single);
                }
            } else if (user.getRoleType().equals(TypeInfo.getCorporationRoleType())) {
                CouponsBatch couponsBatch = couponsBatchService.getById(co.getBatchId());
                SingleCouponVO single = getSingleCouponVO(couponsBatch, co);
                res.add(single);
            }
        });
        return couponsVO;
    }

    @Override
    @PermissionChecker(requiredRole = Role.ADMIN)
    public void deleteCouponByCouponId(Long couponId) {
        Boolean isSuccess = couponsService.removeById(couponId);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_DELETE_ERROR, "delete coupon failed");
        }
    }

    @Override
    public void invalidateCouponById(Long couponId) {
        Coupons coupons = couponsService.getById(couponId);
        if (coupons == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no such coupon");
        }
        coupons.setIsUsed(true);
        Boolean isSuccess = couponsService.updateById(coupons);
        if (!isSuccess) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_UPDATE_ERROR, "invalidate coupon failed");
        }
    }

    private void checkIfDuplicateUserAndBatchId(Long userId, Long batchId) {
        String curMemberKey =  userId+":"+batchId;
        if (!globalCache.sGet(ISMEMBERKEY).contains(curMemberKey)){
            globalCache.sSetAndTime(ISMEMBERKEY, TOTAL_SEC_PER_DAY, curMemberKey);
        } else {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "one person can only have one coupon");
        }
    }

    private void sendToMQ(Coupons co) {
        kafkaTemplate.send(TOPIC_NAME,new Gson().toJson(co)).addCallback(success->{
//            String topic = success.getRecordMetadata().topic();
//            int partition = success.getRecordMetadata().partition();
//            long offset = success.getRecordMetadata().offset();
//            log.info("发送成功:topic="+topic+", partition="+partition+",offset ="+offset + co.toString());
        },failure-> log.warn("发送失败:"+failure.getMessage()));
    }

    public void checkParameters(CouponIndividualDTO couponIndividualDTO) {
        // check if has batchId
        //if (!cachePrepareService.getCouponsBatchBloomFilter().mightContain(couponIndividualDTO.getBatchId())) {
        //    throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "no such coupons batch");
        //}
        User user = userService.getById(couponIndividualDTO.getUserId());
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "no such individual user");
        }
        if (!user.getRoleType().equals(TypeInfo.getIndividualRoleType())) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "user id error");
        }
        Timestamp start = couponIndividualDTO.getValidFrom();
        Timestamp end = couponIndividualDTO.getValidTo();
        if (start == null && end == null) {
            return; // forever valid
        }
        if (start == null || end == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "illegal time");
        }
        if (DateUtil.compare(start, end) <= 0 && DateUtil.compare(end, new Timestamp(System.currentTimeMillis())) <= 0) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "illegal time");
        }
    }

    private CouponsBatchDTO setCouponsBatchDTO(CouponCorpDTO couponCorpDTO) {
        CouponsBatchDTO couponsBatchDTO = new CouponsBatchDTO();
        couponsBatchDTO.setDiscount(couponCorpDTO.getDiscount());
        couponsBatchDTO.setCouponType(TypeInfo.getCouponCorporationType());
        couponsBatchDTO.setStock(0);
        couponsBatchDTO.setDetails(couponCorpDTO.getDetails());
        return couponsBatchDTO;
    }

    private Coupons setNewCoupon(User user, Long batchId) {
        Coupons coupons = new Coupons();
        coupons.setUserId(user.getId());
        coupons.setValidTo(null);
        coupons.setValidFrom(null);
        coupons.setBatchId(batchId);
        coupons.setIsUsed(false);
        return coupons;
    }

    private SingleCouponVO getSingleCouponVO(CouponsBatch couponsBatch, Coupons co) {
        SingleCouponVO single = new SingleCouponVO();
        CouponsBatchVO couponsBatchVO = new CouponsBatchVO();
        couponsBatchVO.setBatchId(couponsBatch.getBatchId());
        couponsBatchVO.setDetails(couponsBatch.getDetails());
        couponsBatchVO.setCouponType(couponsBatch.getCouponType());
        couponsBatchVO.setDiscount(couponsBatch.getDiscount());
        couponsBatchVO.setStock(couponsBatch.getStock());
        single.setCouponsBatchVO(couponsBatchVO);
        single.setCoupons(co);
        return single;
    }

    public Coupons setNewIndividualCoupon(CouponIndividualDTO couponIndividualDTO) {
        Coupons coupons = new Coupons();
        coupons.setUserId(couponIndividualDTO.getUserId());
        coupons.setBatchId(couponIndividualDTO.getBatchId());
        coupons.setIsUsed(false);
        coupons.setValidTo(couponIndividualDTO.getValidTo());
        coupons.setValidFrom(couponIndividualDTO.getValidFrom());
        return coupons;
    }

}
