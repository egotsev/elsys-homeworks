package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryTest {
	private Plus plus = new Plus();
	private Minus minus = new Minus();
	private Divide divide = new Divide();
	private Multiply multiply = new Multiply();
	public static boolean status = false;
	@Before
	public void setUp() throws Exception {
		status = true;
	}

	@After
	public void tearDown() throws Exception {
		status = false;
	}

	@Test
	public void testPlus() {
		assertEquals(10.0, plus.calc(2.0, 8.0), 0.1);
	}
	
	@Test
	public void testMinus() {
		assertEquals(10.0, minus.calc(5.0, 15.0), 0.1);
	}
	
	@Test
	public void testDivide() {
		assertEquals(7.0, divide.calc(8.0, 56.0), 0.1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testDivideByZero() {
		divide.calc(0, 5);
	}
	
	@Test
	public void testMultiply() {
		assertEquals(69.0, multiply.calc(23.0, 3.0), 0.1);
	}

}
