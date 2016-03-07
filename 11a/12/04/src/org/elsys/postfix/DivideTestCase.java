package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

public class DivideTestCase extends PostfixTestCase {

	@Override
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Divide());
	}

	@Override
	public void test() throws Exception {
		Scanner scanner = new Scanner("20 4 /");
		postfix.interpret(scanner);
		Stack<Double> context = postfix.getContext();
		assertEquals(5, context.lastElement(), 0.000000000001);
	}

}
