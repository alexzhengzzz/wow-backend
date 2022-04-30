package com.service.impl;

import com.entity.CorpEmployee;
import com.mapper.CorpEmployeeMapper;
import com.service.ICorpEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CorpEmployeeServiceImpl extends ServiceImpl<CorpEmployeeMapper, CorpEmployee> implements ICorpEmployeeService {

}
