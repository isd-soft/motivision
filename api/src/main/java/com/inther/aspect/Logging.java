package com.inther.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

    public Logger log = Logger.getLogger(Logging.class);

    @Around("execution(* com.inther.controller.*.*(..))")
    public Object before(ProceedingJoinPoint joinPoint){
        long sTime = System.currentTimeMillis();
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("---------------------------------------------------");
        log.info("Starting request " + methodSignature.getName());
        if(joinPoint.getArgs()!=null){
            log.info("Arguments passed ");
            Object[] args = joinPoint.getArgs();
            String[] argsNames = methodSignature.getParameterNames();
            for (int i = 0; i < args.length; i++){
                log.info("Argument " + (i+1) + ": " + argsNames[i] + " = " + args[i].toString());
            }
        }else {
            log.info("No arguments passed");
        }
        Object result = new Object();
        try{
            result = joinPoint.proceed();
        }catch (Throwable throwable) {
            log.error("exeption occured in method " + methodSignature.getName(), throwable);
        }

        log.info("Ending request " + methodSignature.getName());
        long eTime = System.currentTimeMillis();
        log.info("Request handled in " + (eTime-sTime) + " ms");
        return result;
    }
}
