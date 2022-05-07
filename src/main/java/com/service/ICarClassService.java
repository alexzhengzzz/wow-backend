package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.CarClass;
import com.vo.CarClassVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
public interface ICarClassService extends IService<CarClass> {
    CarClass getCarClassInfoById(Integer id);

    List<CarClassVO> getCarClassInfo();

    Integer insert(CarClass carClass);

}
