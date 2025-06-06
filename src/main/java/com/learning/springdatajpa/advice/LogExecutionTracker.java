package com.learning.springdatajpa.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogExecutionTracker {

    @Around( "@annotation(com.learning.springdatajpa.annotation.TrackExecutionTime)")
    public Object logExecutionDuration(ProceedingJoinPoint pjp) throws Throwable {
        //before advice
        long startTime = System.currentTimeMillis();
        Object object = pjp.proceed();
        //after advice
        long endTime = System.currentTimeMillis();
        log.info("method {} execution takes {} ms to complete",pjp.getSignature(),(endTime-startTime));
        return object;
    }
}
