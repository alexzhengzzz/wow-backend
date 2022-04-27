package com.service.impl;

import com.entity.Individual;
import com.mapper.IndividualMapper;
import com.service.IIndividualService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-04-26
 */
@Service
public class IndividualServiceImpl extends ServiceImpl<IndividualMapper, Individual> implements IIndividualService {

}
