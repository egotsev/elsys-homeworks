package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlusTestCase {
	private Plus plusOperation;
	
	@Before
	public void setUp() throws Exception {
		plusOperation = new Plus();
		plusOperation.setContext(new Stack<Double>());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void plusOperationShouldReturnCorrectResult() {
		plusOperation.getContext().push(1.0);
		plusOperation.getContext().push(3.0);
		plusOperation.eval();
		Double correctResult = 4.0;
		assertEquals(correctResult, plusOperation.getContext().pop());
	}
}
