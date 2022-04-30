package com.business;

import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.utils.cache.vo.UserVO;


public interface LoginBusiness {

    UserVO register(RegisterDTO registerDTO);

    UserVO login(LoginDTO loginDTO);
}
