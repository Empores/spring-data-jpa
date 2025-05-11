package com.learning.springdatajpa.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomLoggingFramework {

   // @Around("execution(* com.learning.springdatajpa.*.*.*(..))")
    @Around("@annotation(com.learning.springdatajpa.annotation.LogRequestAndResponse)")
    public Object captureRequestAndResponse(ProceedingJoinPoint pjp) throws Throwable {
        //execute before logic
        System.out.println("================Before=================");
        log.info("execution started :::: {}",pjp.getSignature());
        log.info("Request body ::::: {}", new ObjectMapper().writeValueAsString(pjp.getArgs()));

        System.out.println("================Before end=================");
        Object object = pjp.proceed();

        //execute after logic
        System.out.println("================After=================");
        log.info("execution ended :::: {}",pjp.getSignature());
        log.info("Response body ::::: {}", new ObjectMapper().writeValueAsString(object));
        System.out.println("================After end=================");
        return object;
    }



}
