package com.interceptor;

import com.bean.LoginUser;
import com.context.ServiceContext;
import com.context.ServiceContextHolder;
import com.enums.Role;
import com.enums.RoleType;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.utils.cache.JWTUtils;
import com.utils.cache.impl.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginUser loginUser = new LoginUser();
        String token = getAuthToken(request);
        // set default login user
        if (token == null || token.equals("")) {
            loginUser.setRole(Role.ANONYMOUS);
            loginUser.setRoleType(RoleType.GUEST);
            saveToContext(loginUser);
            return true;
        }

        // validate & update token
        String sub = JWTUtils.validateToken(token);
        if (sub == null || sub.equals(""))
            throw GeneralExceptionFactory.create(ErrorCode.USER_TOKEN_VERIFY_ERROR, "not a valid token");
        if (JWTUtils.isNeedUpdate(token)) {
            String newToken = JWTUtils.createToken(sub);
            response.setHeader(JWTUtils.USER_LOGIN_TOKEN, newToken);
        }

        // try to get loginUser from cache
        String redisKey = "login:" + sub;
        loginUser = (LoginUser) redisCacheManager.get(redisKey);
        if (loginUser == null) {
            throw GeneralExceptionFactory.create(ErrorCode.USER_TOKEN_VERIFY_ERROR, "login state expired, please login again");
        }
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
        ServiceContextHolder.clear();
    }

    private void saveToContext(LoginUser loginUser) {
        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setLoginUser(loginUser);
        ServiceContextHolder.setServiceContext(serviceContext);
    }

}