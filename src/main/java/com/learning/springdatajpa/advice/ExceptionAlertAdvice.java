package com.learning.springdatajpa.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionAlertAdvice {

    Logger logger = LoggerFactory.getLogger(ExceptionAlertAdvice.class);

    @Pointcut(value = "execution(* com.learning.springdatajpa.controller.ProductController.*(..))")
    private void alertFor(){

    }

    @AfterThrowing(value = "alertFor()",throwing = "e")
    public void captureErrorAndSetAlerts(JoinPoint jp,Exception e){
        log.error("Exception occurs in {}",jp.getSignature());
        log.error("Exception occurs in {}",e.getMessage());
        if(e instanceof IllegalArgumentException){
            System.out.println("hi Illegal argument exception");
        }
        if(e instanceof ArithmeticException){
            System.out.println("hi Arithmetic Exception");
        }
    }
}
