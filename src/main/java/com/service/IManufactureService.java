package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Manufacture;
import com.vo.ManufactureVO;

import java.util.List;

public interface IManufactureService extends IService<Manufacture> {

    List<ManufactureVO> getManufactureInfo();
}
