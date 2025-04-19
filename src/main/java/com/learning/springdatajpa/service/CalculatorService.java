package com.learning.springdatajpa.service;

public class CalculatorService {

    public int sum(int a, int b){
        if(a<0 || b<0){
            throw new RuntimeException("Negative values not allowed");
        }
        return a+b;
    }

}
