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
	}

	@After
	public void tearDown() throws Exception {
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
		Scanner scanner = new Scanner("20 10 -");
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
		Scanner scanner = new Scanner("20 10 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(2.0, context.pop(), 0.000001);
	}
}
