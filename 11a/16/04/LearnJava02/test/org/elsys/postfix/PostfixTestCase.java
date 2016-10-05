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
	public void testPlus() {
		Scanner scanner = new Scanner("80 40 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(120.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testMinus() {
		Scanner scanner = new Scanner("40 80 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-40.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testMultiply() {
		Scanner scanner = new Scanner("40 80 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(3200.0, context.pop(), 0.000001);
	}
	@Test
	public void testDivide() {
		Scanner scanner = new Scanner("80 40 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(2.0, context.pop(), 0.000001);
	}

	@Test(expected = IllegalStateException.class)
	public void testExceptionUnknown() {
		Scanner scanner = new Scanner("80 40 k");
		postfix.interpret(scanner);
	}

}
