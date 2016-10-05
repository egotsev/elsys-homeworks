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
	public void tearDown() throws Exception {}

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
		Scanner scanner = new Scanner("10 8 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(80.0, context.pop(), 0.000001);
	}

	@Test
	public void testDivide(){
		Scanner scanner = new Scanner("2 10 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(5.0, context.pop(), 0.000001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDivideBy0() {
		Scanner scanner = new Scanner("0 10 /");
		postfix.interpret(scanner);
		
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testUnknownOperation() {
		Scanner scanner = new Scanner("0 10 N");
		postfix.interpret(scanner);
	}
}
