package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UserServiceLoggingAspect {

    @Pointcut("execution(* com.example.demo.service.UserService.*(..))")
    public void userServiceMethods() {}

    @AfterReturning(value = "userServiceMethods()", returning = "result")
    public void logUserServiceMethods(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {} from class UserService is called", methodName);
    }
}
