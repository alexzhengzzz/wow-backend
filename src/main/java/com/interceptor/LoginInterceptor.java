package com.interceptor;

import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.utils.cache.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //http的header中获得token
        String token = getAuthToken(request);

        //token不存在
        if (token == null || token.equals("")) throw GeneralExceptionFactory.create(ErrorCode.USER_TOKEN_VERIFY_ERROR, token);
        //验证token
        String sub = JWTUtils.validateToken(token);
        if (sub == null || sub.equals(""))
            throw GeneralExceptionFactory.create(ErrorCode.USER_TOKEN_VERIFY_ERROR, token);
        //更新token有效时间 (如果需要更新其实就是产生一个新的token)
        if (JWTUtils.isNeedUpdate(token)){
            String newToken = JWTUtils.createToken(sub);
            response.setHeader(JWTUtils.USER_LOGIN_TOKEN,newToken);
        }
        return true;
    }

    private String getAuthToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null) {
            token = request.getParameter("Authorization");
        }
        return token;
    }
}