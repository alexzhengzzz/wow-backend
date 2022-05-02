package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Corporation;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zmh
 * @since 2022-04-26
 */
public interface ICorporationService extends IService<Corporation> {
    Corporation getCorporationByName(String name);
}
