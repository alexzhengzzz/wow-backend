package com.aspect;

import com.annotation.SystemLog;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.annotation.SystemLog)")
    public void pt(){
        //joinPoint for around
    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
            handleAfter(result);
        } finally {
            log.info("------------------end------------------" + System.lineSeparator());
        }
        return result;
    }
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw GeneralExceptionFactory.create(ErrorCode.UNKNOWN_ERROR, "bad request");
        }
        HttpServletRequest request = attributes.getRequest();
        SystemLog systemLog = getSystemLog(joinPoint);
        log.info("------------------start------------------");
        log.info("URL: {}", request.getRequestURL());
        log.info("BusinessName: {}", systemLog.businessName());
        log.info("HTTP Method: {}", request.getMethod());
        log.info("ClassMethod: {}, {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("IP: {}", request.getRemoteHost());
        log.info("Request: {}", joinPoint.getArgs());
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }

    private void handleAfter(Object result) {
        log.info("Response: {}", result);
    }

}
