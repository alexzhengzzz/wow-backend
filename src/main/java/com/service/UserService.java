package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bean.Response;
import com.dto.LoginDTO;
import com.entity.User;
import com.vo.UserVO;

/**
 * 根据需求制定业务逻辑
 */
public interface UserService extends IService<User> {

    Response<UserVO> login(LoginDTO loginDTO) throws Exception;
}
