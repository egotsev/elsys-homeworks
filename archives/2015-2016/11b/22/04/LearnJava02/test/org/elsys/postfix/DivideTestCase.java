package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DivideTestCase {
	private Divide divideOperation;
	
	@Before
	public void setUp() throws Exception {
		divideOperation = new Divide();
		divideOperation.setContext(new Stack<Double>());
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void plusOperationShouldReturnCorrectResult() {
		divideOperation.getContext().push(5.0);
		divideOperation.getContext().push(3.0);
		divideOperation.eval();
		Double correctResult = 5.0 / 3.0;
		assertEquals(correctResult, divideOperation.getContext().pop());
	}
}
