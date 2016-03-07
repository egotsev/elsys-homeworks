package org.elsys.postfix;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryOperationTestCase {
	private BinaryOperation binaryOp;

	@Before
	public void setUp() throws Exception {
		binaryOp = new Multiply();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void binaryOperationMultiplicatoinTest() {
		Stack<Double> context = new Stack<Double>();
		context.push(5.7);
		context.push(10.0);
		
		binaryOp.setContext(context);
		binaryOp.eval();
		
		double expectedValue = binaryOp.getContext().peek();
		double delta = 0.0001d;
		assertEquals(57.0, expectedValue, delta);
	}

}
