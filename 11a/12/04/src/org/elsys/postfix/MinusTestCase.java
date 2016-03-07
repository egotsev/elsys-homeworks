package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

public class MinusTestCase extends PostfixTestCase {

	@Override
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Minus());
	}

	@Override
	public void test() throws Exception {
		Scanner scanner = new Scanner("5 4 -");
		postfix.interpret(scanner);
		Stack<Double> context = postfix.getContext();
		assertEquals(1, context.lastElement(), 0.000000000001);
	}

}
