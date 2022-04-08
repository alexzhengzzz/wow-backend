package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dto.User;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 继承mybatis-plus<M, T>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
