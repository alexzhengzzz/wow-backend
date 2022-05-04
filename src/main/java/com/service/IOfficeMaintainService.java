package com.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Office;
import com.vo.OfficeVO;

import java.util.List;

@Deprecated
public interface IOfficeMaintainService extends IService<Office>{


    List<OfficeVO> getOfficeInfo();
}
