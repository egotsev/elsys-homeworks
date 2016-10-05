package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MultiplyTestCase {
	private Multiply multiplyOperation;
	
	@Before
	public void setUp() throws Exception {
		multiplyOperation = new Multiply();
		multiplyOperation.setContext(new Stack<Double>());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void plusOperationShouldReturnCorrectResult() {
		multiplyOperation.getContext().push(5.0);
		multiplyOperation.getContext().push(3.0);
		multiplyOperation.eval();
		Double correctResult = 5.0 * 3.0;
		assertEquals(correctResult, multiplyOperation.getContext().pop());
	}
}
