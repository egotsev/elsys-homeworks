package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

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

	@Test
	public void testPlus() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testMinus() {
		Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(10.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testMultiply() {
		Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testDivide() {
		Scanner scanner = new Scanner("10 20 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(2.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testExpression() {
		Scanner scanner = new Scanner("10 20 / 3 * 4 + 2 - 6 7 8 * * /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-42.0, context.pop(), 0.000001);
	}
	
	@Test(expected=java.lang.ArithmeticException.class)
	public void testDivisionByZero() {
		Scanner scanner = new Scanner("0 2 /");
		postfix.interpret(scanner);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testUnknownOperator() {
		Scanner scanner = new Scanner("10 2 y");
		postfix.interpret(scanner);
	}
}
