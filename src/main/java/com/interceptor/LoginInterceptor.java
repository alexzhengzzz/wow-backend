package com.interceptor;

import com.bean.LoginUser;
import com.context.ServiceContext;
import com.context.ServiceContextHolder;
import com.enums.Role;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.utils.cache.IGlobalCache;
import com.utils.cache.JWTUtils;
import com.utils.cache.impl.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //http的header中获得token
        LoginUser loginUser = new LoginUser();
        String token = getAuthToken(request);
        //token不存在
        if (token == null || token.equals("")) {
            loginUser.setRole(Role.ANONYMOUS);
            saveToContext(loginUser);
            return true;
        }
        //验证token
        String sub = JWTUtils.validateToken(token);
        if (sub == null || sub.equals(""))
            throw GeneralExceptionFactory.create(ErrorCode.USER_TOKEN_VERIFY_ERROR, token);
        //更新token有效时间 (如果需要更新其实就是产生一个新的token)
        if (JWTUtils.isNeedUpdate(token)) {
            String newToken = JWTUtils.createToken(sub);
            response.setHeader(JWTUtils.USER_LOGIN_TOKEN, newToken);
        }
        // 登录过后解析出token中的用户信息
        String redisKey = "login:" + sub;
//        log.debug("redisKey:" + redisKey);
        loginUser = (LoginUser) redisCacheManager.get(redisKey);
        saveToContext(loginUser);
        return true;
    }

    private String getAuthToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null) {
            token = request.getParameter("Authorization");
        }
        return token;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ServiceContextHolder.setServiceContext(null);
    }

    private void saveToContext(LoginUser loginUser) {
        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setLoginUser(loginUser);
        ServiceContextHolder.setServiceContext(serviceContext);
    }
}