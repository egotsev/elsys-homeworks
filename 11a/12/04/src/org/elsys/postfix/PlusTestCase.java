package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

public class PlusTestCase extends PostfixTestCase {

	@Override
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Plus());
	}

	@Override
	public void test() throws Exception {
		Scanner scanner = new Scanner("5 4 +");
		postfix.interpret(scanner);
		Stack<Double> context = postfix.getContext();
		assertEquals(9, context.lastElement(), 0.000000000001);
	}

}
