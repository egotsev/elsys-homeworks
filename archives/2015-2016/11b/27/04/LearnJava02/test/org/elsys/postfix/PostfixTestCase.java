package org.elsys.postfix;

import static org.junit.Assert.assertEquals;
import java.security.InvalidParameterException;

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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test
	public void MinusShouldReturnCorrectResult() {
		Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-10.0, context.pop(), 0.000001);
	}
	
	@Test
	public void MultiplyShouldReturnCorrectResult() {
		Postfix postfix = new Postfix();
		postfix.addOperation(new Multiply());
		
		Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void DivideShouldNotDivideOnZero() {
		Postfix postfix = new Postfix();
		postfix.addOperation(new Divide());
		Scanner scanner = new Scanner("10 0 /");
		postfix.interpret(scanner);
	}
	
	@Test
	public void PostfixShouldHandleInvalidOperation() {
		Postfix postfix = new Postfix();
		postfix.addOperation(new Plus());
		
		Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		scanner.close();
		assertEquals(postfix.getContext().size() > 1, true);
	}
	
	@Test(expected=IllegalStateException.class)
	public void EvalShouldThrowIfStackContainsLessThanTwoElements() {
		Scanner scanner = new Scanner("10 +");
		postfix.interpret(scanner);
	}
}
