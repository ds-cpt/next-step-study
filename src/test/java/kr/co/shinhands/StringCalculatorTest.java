package kr.co.shinhands;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.co.shinhands.StringCalculator;

class StringCalculatorTest {
	StringCalculator calculator;

	@BeforeEach
	public void setUp() {
		calculator = new StringCalculator();
	};

	@Test
	public void add_null_or_empty() {
		assertEquals(0, calculator.add(null));
		assertEquals(0, calculator.add(""));
	}

	@Test
	public void add_one_number() {
		assertEquals(1, calculator.add("1"));
	}

	@Test
	public void add_comma_delimited_numbers() {
		assertEquals(3, calculator.add("1,2"));
	}

	@Test
	public void add_comma_delimited_or_colon_delimited_numbers() {
		assertEquals(6, calculator.add("1,2:3"));
	}

	@Test
	public void add_custom_delimited_numbers() {
		assertEquals(6, calculator.add("//;\n1;2;3"));
	}
	@Test
	public void add_negative_number() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			calculator.add("-1,2,3");
		});
	}


}