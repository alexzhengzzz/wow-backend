package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.User;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-04-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    public User getUserById(Long id) {
        User user = this.baseMapper.selectById(id);
        if (user == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "User id not found");
        }
        return user;
    }
}
