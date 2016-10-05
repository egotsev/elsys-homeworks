package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

public class BinaryOperationTestCase {
	private BinaryOperation binaryOperation;
	
	@Before
	public void setUp() throws Exception {
		binaryOperation = new Plus();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testSingleOperand() {
		Stack<Double> context = new Stack<Double>();
		context.push(2.0);
		
		binaryOperation.setContext(context);
		
		binaryOperation.eval();
	}
	
	@Test
	public void testOperandsPoppedAndResultPushedToContext() {
		Stack<Double> context = new Stack<Double>();
		context.push(2.0);
		context.push(2.0);
		
		binaryOperation.setContext(context);
		binaryOperation.eval();
		
		assertEquals(4.0, binaryOperation.getContext().peek(), 0.000001);
		assertEquals(1, binaryOperation.getContext().size());
	}
}