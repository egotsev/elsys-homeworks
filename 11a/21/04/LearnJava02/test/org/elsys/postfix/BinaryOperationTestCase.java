package org.elsys.postfix;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class BinaryOperationTestCase {
	BinaryOperation op;
	
	@Before
	public void setUp() throws Exception {
		op = new Plus();
		op.setContext(new Stack<Double>());
	}

	@Test(expected = IllegalStateException.class)
	public void testExceptionWhenNotEnoughValues() {
		op.getContext().push(12.0);
		op.eval();
	}
}
