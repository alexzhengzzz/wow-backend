package com.business;

import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.vo.TokenInfoVO;
import com.vo.UserVO;


public interface LoginBusiness {

    UserVO register(RegisterDTO registerDTO);

    UserVO login(LoginDTO loginDTO);

    TokenInfoVO refreshToken(String token);
}
