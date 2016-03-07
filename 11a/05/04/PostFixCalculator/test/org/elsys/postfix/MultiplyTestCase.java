package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MultiplyTestCase {

	private Stack<Double> testValues = new Stack<Double>();
	Multiply multiply = new Multiply();
	
	@Before
	public void setUp() throws Exception {
		multiply.setContext(testValues);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		testValues.push(10.0);
		testValues.push(40.0);
		multiply.eval();
		
		assertEquals(1, multiply.getContext().size());
		assertEquals(400.0, multiply.getContext().pop(), 0.000001);
	}

	@Test(expected=IllegalStateException.class)
	public void testNumberOfArguments() {
		testValues.push(10.0);
		multiply.eval();
	}
}