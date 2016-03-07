package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PostFixTestCase {

	private Postfix postfix;
	
	@Before
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Plus());
		postfix.addOperation(new Minus());
		postfix.addOperation(new Multiply());
		postfix.addOperation(new Divide());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOperations() {
		Scanner scanner = new Scanner("10 20 + 50 - 5 * 200 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(2.0, context.pop(), 0.000001);
	}
	
	@Test(expected=NumberFormatException.class)
	public void testUnknownOperation() {
		Scanner scanner = new Scanner("50 50 ^");
		postfix.interpret(scanner);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDivisionByZero() {
		Scanner scanner = new Scanner("0 10 /");
		postfix.interpret(scanner);

	}

}
