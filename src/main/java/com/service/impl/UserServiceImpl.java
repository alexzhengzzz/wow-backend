package com.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bean.Response;
import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.entity.User;
import com.exception.ErrorCode;
import com.exception.GeneralException;
import com.exception.GeneralExceptionFactory;
import com.mapper.UserMapper;
import com.service.UserService;
import com.utils.cache.IGlobalCache;
import com.utils.cache.JWTUtils;
import com.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

