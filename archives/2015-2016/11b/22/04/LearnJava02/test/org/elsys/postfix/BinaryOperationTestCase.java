package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryOperationTestCase {
	private BinaryOperation binaryOperation;
	
	@Before
	public void setUp() throws Exception {
		binaryOperation = new Plus();
		binaryOperation.setContext(new Stack<Double>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalStateException.class)
	public void shouldThrowWhenContextContainsLessThanTwoElements() {
		binaryOperation.getContext().push(1.0);
		binaryOperation.eval();
	}
	
	@Test
	public void evalShouldPushResultToContextStack() {
		binaryOperation.getContext().push(1.0);
		binaryOperation.getContext().push(3.0);
		binaryOperation.eval();
		Double correctResult = 4.0;
		assertEquals(correctResult, binaryOperation.getContext().pop());
	}
}
