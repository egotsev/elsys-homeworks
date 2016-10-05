package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BinaryOperationTestCase {
	private Plus plus;
	private Minus minus;
	private Divide divide;
	private Multiply multiply;

	@Before
	public void setUp() throws Exception {
		plus = new Plus();
		minus = new Minus();
		divide = new Divide();
		multiply = new Multiply();
	}

	@Test
	public void testPlus() {
		assertEquals(1.0, plus.calc(0.95, 0.05), 0.1);
	}

	@Test
	public void testMinus() {
		assertEquals(1.0, minus.calc(0.95, 1.95), 0.1);
	}

	@Test
	public void testDivide() {
		assertEquals(100, divide.calc(1000.0, 100000.0), 0.1);
	}

	@Test
	public void testMultiply() {
		assertEquals(225.0, multiply.calc(15.0, 15.0), 0.1);
	}
}
