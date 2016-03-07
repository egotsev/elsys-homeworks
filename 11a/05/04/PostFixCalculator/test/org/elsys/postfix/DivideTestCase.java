package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DivideTestCase {

	private Stack<Double> testValues = new Stack<Double>();
	Divide divide = new Divide();
	
	@Before
	public void setUp() throws Exception {
		divide.setContext(testValues);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDivision() {
		testValues.push(10.0);
		testValues.push(40.0);
		divide.eval();
		
		assertEquals(1, divide.getContext().size());
		assertEquals(4.0, divide.getContext().pop(), 0.000001);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDivisionByZero() {
		testValues.push(0.0);
		testValues.push(10.0);
		divide.eval();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testNumberOfArguments() {
		testValues.push(10.0);
		divide.eval();
	}
	
}