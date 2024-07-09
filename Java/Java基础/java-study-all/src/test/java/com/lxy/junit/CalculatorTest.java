package com.lxy.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void testAdd() {
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(1,2));
    }
}