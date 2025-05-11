package com.learning.springdatajpa.advice;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MetricsRegistryAdvice {

    @Autowired
    private ObservationRegistry registry;

   // @After(value = "execution (* com.learning.springdatajpa.controller.ProductController.*(..))")
    public void sendMetrics(JoinPoint jp){
        log.info("application collecting metrics");
        Observation.createNotStarted(jp.getSignature().getName(),registry).observe(jp::getArgs);
        log.info("application publish metrics");
    }

}
