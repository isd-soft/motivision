package com.inther.aspect;


import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class ExceptionLoggerPointCut {
    @AfterThrowing(pointcut = "execution(* com.inther.*.*.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        ex.printStackTrace();
    }
}