package com.aspect;

import com.annotation.PermissionChecker;
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
        // pointcut
    }

    @Around("pt()")
    public Object check(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
        } finally {
           log.info("pt()");
        }
        return result;
    }
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServiceContext serviceContext = ServiceContextHolder.getServiceContext();
        LoginUser loginUser = serviceContext.getLoginUser();
        PermissionChecker permissionChecker = getPermissionChecker(joinPoint);
        Role role = permissionChecker.requiredRole();
        RoleType roleType = permissionChecker.requiredRoleType();
        checkPermission(role, roleType, loginUser);
    }

    private PermissionChecker getPermissionChecker(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(PermissionChecker.class);
    }

    private void checkPermission(Role role, RoleType roleType, LoginUser loginUser) {
        if (loginUser == null) {
            defaultCheck(role, roleType);
        } else {
            roleLevelCheck(role, loginUser);
            roleTypeCheck(roleType, loginUser);
        }
    }
    private void defaultCheck(Role requiredRole, RoleType requiredRoleType) {
        if (requiredRole != Role.ANONYMOUS || requiredRoleType != RoleType.GUEST) {
            throw GeneralExceptionFactory.create(ErrorCode.PERMISSION_DENIED, "no legal login info");
        }
    }
    // role level admin 0 , user 1, guest 2
    private void roleLevelCheck(Role requiredRole, LoginUser loginUser) {
        if (loginUser.getRole().ordinal() > requiredRole.ordinal()) {
            throw GeneralExceptionFactory.create(ErrorCode.PERMISSION_DENIED, "Permission denied current role: " + loginUser.getRole(), "need role: " + requiredRole);
        }
    }
    // check whether requiredRoleType is admin or requiredRoleType is the same as login user
    private void roleTypeCheck(RoleType requiredRoleType, LoginUser loginUser) {
        if (loginUser.getRoleType() == RoleType.ADMIN) {
            return;
        }
        if (requiredRoleType ==  null || requiredRoleType == RoleType.GUEST) {
            return; // guest is not check
        }
        if (requiredRoleType != loginUser.getRoleType()) {
            throw GeneralExceptionFactory.create(ErrorCode.PERMISSION_DENIED, "Permission denied current roleType: " + loginUser.getRoleType(), "need roleType: " + requiredRoleType + " or admin");
        }
    }
}
