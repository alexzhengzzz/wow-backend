package com.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Office;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.OfficeMapper;
import com.service.OfficeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Service
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements OfficeService {
    public Office getLocById(Integer id) {
        Office office = this.baseMapper.selectById(id);
        if (office == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "loc id not found");
        }
        return office;
    }
}
