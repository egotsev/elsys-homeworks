package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryOperationTestCase {

	private Stack<Double> testValues = new Stack<Double>();
	BinaryOperation op = new Plus();
	
	@Before
	public void setUp() throws Exception {
		op.setContext(testValues);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		testValues.push(40.0);
		testValues.push(40.0);
		op.eval();
		
		assertEquals(1, op.getContext().size());
		assertEquals(80.0, op.getContext().pop(), 0.000001);
	}

	@Test(expected=IllegalStateException.class)
	public void testNumberOfArguments() {
		testValues.push(10.0);
		op.eval();
	}
}