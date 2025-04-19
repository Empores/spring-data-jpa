package com.learning.springdatajpa;

import com.learning.springdatajpa.service.CalculatorService;
import org.junit.Assert;
import org.junit.Test;


public class CalculatorServiceTest {

    @Test(expected = RuntimeException.class)
    public void testSum(){
        CalculatorService service = new CalculatorService();
        int a=10;
        int b=5;
        int actualResult = service.sum(a,b);
        Assert.assertEquals(15,actualResult);
        Assert.assertEquals(2,service.sum(10,-8));
    }

}
