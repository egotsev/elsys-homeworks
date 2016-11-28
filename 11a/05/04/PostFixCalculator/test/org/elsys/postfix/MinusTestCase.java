package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MinusTestCase {

	private Stack<Double> testValues = new Stack<Double>();
	Minus minus = new Minus();
	
	@Before
	public void setUp() throws Exception {
		minus.setContext(testValues);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		testValues.push(10.0);
		testValues.push(40.0);
		minus.eval();
		
		assertEquals(1, minus.getContext().size());
		assertEquals(30.0, minus.getContext().pop(), 0.000001);
	}

	@Test(expected=IllegalStateException.class)
	public void testNumberOfArguments() {
		testValues.push(10.0);
		minus.eval();
	}
}