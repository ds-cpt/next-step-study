package com.java.nextstep;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {

    StringCalculator strCal;

    @BeforeEach
    void setup() {
        strCal = new StringCalculator();
    }

    @Test
    void add_null() {
        assertEquals(0, strCal.add(""));
        assertEquals(0, strCal.add(null));
    }

    @Test
    void add_숫자하나() {
        assertEquals(5, strCal.add("5"));
    }

    @Test
    void add_쉼표숫자() {
        assertEquals(10, strCal.add("2,3,5"));
    }

    @Test
    void add_세미콜론() {
        assertEquals(12, strCal.add("2,4:6"));
    }

    @Test
    void add_çustom() {
        assertEquals(6, strCal.add("//;\n1;2;3"));
    }

    @Test
    void add_음수() throws Exception {
        assertThrows(RuntimeException.class, () -> strCal.add("-1,1,4"));
    }

}