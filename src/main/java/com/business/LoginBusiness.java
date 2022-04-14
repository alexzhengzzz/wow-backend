package com.business;

import com.dto.LoginDTO;
import com.dto.RegisterDTO;
import com.vo.UserVO;
import org.springframework.stereotype.Component;


public interface LoginBusiness {

    UserVO register(RegisterDTO registerDTO);

    UserVO login(LoginDTO loginDTO);
}
