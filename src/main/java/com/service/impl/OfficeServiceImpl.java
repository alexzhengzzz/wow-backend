package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Office;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.OfficeMapper;
import com.service.IOfficeService;
import com.service.util.OfficeUtil;
import com.vo.OfficeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Service
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements IOfficeService {
    public Office getLocById(Integer id) {
        Office office = this.baseMapper.selectById(id);
        if (office == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "loc id not found");
        }
        return office;
    }

    public List<OfficeVO> getOfficeInfo(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select();
        List<Office> officeList = this.baseMapper.selectList(queryWrapper);
        if(officeList == null){
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "Office Table is Empty");
        }
        return OfficeUtil.transferList(officeList);
    }

    @Override
    public int insert(Office office) {
        return this.baseMapper.insert(office);
    }

//    @Override
//    public boolean createOfficeInfo(OfficeDTO officeDTO) {
//        this.baseMapper.update(OfficeUtil.transferDTO(officeDTO));
//        return true;
//    }
}
