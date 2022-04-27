package com.service;

import com.entity.CorpEmployee;
import com.mapper.CorpEmployeeMapper;
import com.service.ICorpEmployeeService;
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
public class CorpEmployeeServiceImpl extends ServiceImpl<CorpEmployeeMapper, CorpEmployee> implements ICorpEmployeeService {

}
