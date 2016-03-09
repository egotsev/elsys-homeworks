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
	public void postfixPlusTest() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test
	public void postfixMinusTest() {
		Scanner scanner = new Scanner("20 10 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(10.0, context.pop(), 0.000001);
	}
	
	@Test
	public void postfixMultiplyTest() {
		Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
	
	@Test
	public void postfixDivideTest() {
		Scanner scanner = new Scanner("20 10 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(2.0, context.pop(), 0.000001);
	}
	
	@Test(expected = IllegalStateException.class)
	public void binaryOperationOneArgumentTest() {
		Scanner scanner = new Scanner("10 +");
		
		postfix.interpret(scanner);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void binaryOperationZeroDivisionTest() {
		Scanner scanner = new Scanner("10 0 /");
		
		postfix.interpret(scanner);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void binaryOperationUnknownOperationTest() {
		Scanner scanner = new Scanner("10 20 @");
		
		postfix.interpret(scanner);
	}
	
	@Test(expected = IllegalStateException.class)
	public void binaryOperationDifferentStateTest() {
		Scanner scanner = new Scanner("10 + 20");
		
		postfix.interpret(scanner);
	}
	
}
