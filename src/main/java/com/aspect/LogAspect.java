package com.aspect;

import com.annotation.SystemLog;
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

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
            handleAfter(joinPoint, result);
        } finally {
            log.info("------------------end------------------" + System.lineSeparator());
        }
        return result;
    }
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
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
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

    private void handleAfter(ProceedingJoinPoint joinPoint, Object result) {
        log.info("Response: {}", result);
    }

}
