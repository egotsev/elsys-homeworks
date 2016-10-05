package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MinusTestCase {
	private Minus minusOperation;
	
	@Before
	public void setUp() throws Exception {
		minusOperation = new Minus();
		minusOperation.setContext(new Stack<Double>());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void plusOperationShouldReturnCorrectResult() {
		minusOperation.getContext().push(1.0);
		minusOperation.getContext().push(3.0);
		minusOperation.eval();
		Double correctResult = -2.0;
		assertEquals(correctResult, minusOperation.getContext().pop());
	}
}
