package com.aspect;

import com.annotation.PermissionChecker;
import com.annotation.SystemLog;
import com.bean.LoginUser;
import com.context.ServiceContext;
import com.context.ServiceContextHolder;
import com.enums.Role;
import com.enums.RoleType;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class PermitAspect {
    @Pointcut("@annotation(com.annotation.PermissionChecker)")
    public void pt(){

    }

    @Around("pt()")
    public Object check(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
        } finally {
        }
        return result;
    }
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServiceContext serviceContext = ServiceContextHolder.getServiceContext();
        LoginUser loginUser = serviceContext.getLoginUser();
        PermissionChecker permissionChecker = getPermissionChecker(joinPoint);
        Role role = permissionChecker.requiredRole();
        // check user group
        if (loginUser == null) {
            if (role != Role.ANONYMOUS) throw GeneralExceptionFactory.create(ErrorCode.PERMISSION_DENIED);
        } else if (loginUser.getRole().ordinal() > permissionChecker.requiredRole().ordinal()) {
            throw GeneralExceptionFactory.create(ErrorCode.PERMISSION_DENIED, "Permission denied");
        }
        // check required roletype
        RoleType roleType = permissionChecker.requiredRoleType();
        if (roleType ==  null || roleType == RoleType.GUEST) return;
        if (roleType != loginUser.getRoleType() && loginUser.getRoleType() != RoleType.ADMIN) {
            throw GeneralExceptionFactory.create(ErrorCode.PERMISSION_DENIED, "Permission denied");
        }
    }

    private PermissionChecker getPermissionChecker(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        PermissionChecker permissionChecker = methodSignature.getMethod().getAnnotation(PermissionChecker.class);
        return permissionChecker;
    }
}
