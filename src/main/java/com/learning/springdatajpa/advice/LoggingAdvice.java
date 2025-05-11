package com.learning.springdatajpa.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAdvice {
    Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

   // @Pointcut("execution(* com.learning.springdatajpa.*.*.*(..))")
    //@Pointcut("within(com.learning.springdatajpa..*)")
   // @Pointcut("target(com.learning.springdatajpa.service.ProductService)")
    // @Pointcut("execution(* com.learning.springdatajpa.service.ProductService.save*(..))")
     //@Pointcut("execution(* com.learning.springdatajpa.service.ProductService.find*(int))")
    @Pointcut("execution(* com.learning.springdatajpa.controller.ProductController.*(..)) ||" +
            " execution(* com.learning.springdatajpa.service.ProductService.*(..))")

    private void logPointCut(){

    }


 //  @Before("logPointCut()")
  //  @Before(value = "execution(* com.learning.springdatajpa.*.*.*(..))")
  //  @Before(value = "execution(* com.learning.springdatajpa.controller.ProductController.*(..))")
    //execution(public com.learning.springdatajpa.package.class.method explanation
    //execution(* com.learning.springdatajpa.controller.ProductController.addProduct
    public void logRequest(JoinPoint jp) throws JsonProcessingException {
        logger.info("Logging Request ::::::::class name {}, method name {}",
                jp.getTarget(),jp.getSignature().getName());
        logger.info("Logging Request Body {}" , new ObjectMapper().writeValueAsString(jp.getArgs()));
    }

/*
    @AfterReturning(value = "logPointCut()", returning ="object")
    public void logResponse(JoinPoint jp,Object object) throws JsonProcessingException {
        logger.info("LoggingAdvice::::: class name {}, method name {}",
                jp.getTarget(),jp.getSignature().getName());
        logger.info("LoggingAdvice:::::: Response Body {}" , new ObjectMapper().writeValueAsString(object));
    }
*/

  //  @After(value = "logPointCut()")
    public void logResponse(JoinPoint jp) throws JsonProcessingException {
        logger.info("Logging Response::::: class name {}, method name {}",
                jp.getTarget(),jp.getSignature().getName());
        logger.info("Logging Response:::::: Response Body {}" , new ObjectMapper().writeValueAsString(jp.getArgs()));
    }
}


