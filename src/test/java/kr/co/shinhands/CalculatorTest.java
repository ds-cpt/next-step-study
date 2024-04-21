package kr.co.shinhands;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.co.shinhands.Calculator;

class CalculatorTest {
	Calculator calculator;
	@BeforeEach
	public void setUp() {
		calculator = new Calculator();
		System.out.println("Before each test");
	}

	@AfterEach
	public void tearDown() {
		calculator = null;
		System.out.println("After each test");
	}

	@Test
	public void testAdd() {
		assertEquals(5, calculator.add(2, 3));
	}

	@Test
	public void testSubtract() {
		assertEquals(1, calculator.subtract(3, 2));
	}
}