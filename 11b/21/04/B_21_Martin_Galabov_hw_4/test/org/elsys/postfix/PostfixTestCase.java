package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PostfixTestCase {

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
	public void test_plus() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test
	public void test_minus() {
		Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-10.0, context.pop(), 0.000001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_divide() {
		Scanner scanner = new Scanner("10 20 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(0.5, context.pop(), 0.000001);
		
		Scanner scanner_for_error = new Scanner ("10 0 /");
		postfix.interpret(scanner_for_error);
		
	}
	
	@Test
	public void test_multiply() {
		Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
	
	@Test(expected = IllegalStateException.class)
	public void test_few_arguments() {
		Scanner scanner = new Scanner("20 *");
		postfix.interpret(scanner);
	}
	
	@Test(expected = NumberFormatException.class)
	public void unknown_operation() {
		Scanner scanner = new Scanner("20 30 %");
		postfix.interpret(scanner);
	}


}
