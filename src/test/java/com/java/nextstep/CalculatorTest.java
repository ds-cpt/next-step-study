package com.java.nextstep;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CalculatorTest {

    private Calculator cal;

    @BeforeEach
    public void setup() {
        cal = new Calculator();
        System.out.println("setup");
    }

    @Test
    public void add() {
        assertEquals(9,cal.add(6,3));
    }

    @Test
    public void subtract() {
        assertEquals(3, cal.subtract(9,6));
    }

    @Test
    public void mutiply() {
        assertEquals(16, cal.multiply(2,8));
    }

    @Test
    public void divide() {
        assertEquals(5, cal.divide(10,2));
    }


}