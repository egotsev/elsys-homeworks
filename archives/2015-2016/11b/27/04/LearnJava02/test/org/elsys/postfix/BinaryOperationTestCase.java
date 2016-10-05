package org.elsys.postfix;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Test;

public class BinaryOperationTestCase {

	@Test
	public void PlusShouldReturnSumOfGivenParameters() {
		BinaryOperation plus = new Plus();
		double result = plus.calc(1, 2);
		assertEquals(3.0, result, 0.000001);
	}
	
	@Test
	public void MinusShouldReturnSubtractionOfGivenParameters() {
		BinaryOperation plus = new Plus();
		double result = plus.calc(1, 2);
		assertEquals(3.0, result, 0.000001);
	}
	
	@Test
	public void MultiplyShouldReturnProductOfGivenParameters() {
		BinaryOperation multiply = new Multiply();
		double result = multiply.calc(1, 2);
		assertEquals(2.0, result, 0.000001);
	}
	
	@Test
	public void DivideShouldReturnDivisionOfGivenParameters() {
		BinaryOperation divide = new Divide();
		double result = divide.calc(1, 2);
		assertEquals(0.5, result, 0.000001);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void DivideShouldThrowIfSecondParameterIsZero() {
		BinaryOperation divide = new Divide();
		divide.calc(1, 0);
	}
}
