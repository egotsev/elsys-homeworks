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
	public void plusOperationShouldReturnCorrectResult() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test
	public void minusOperationShouldReturnCorrectResult() {
		Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-10.0, context.pop(), 0.000001);
	}
	
	@Test
	public void multiplyOperationShouldReturnCorrectResult() {
		Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
	
	@Test
	public void divideOperationShouldReturnCorrectResult() {
		Scanner scanner = new Scanner("10 20 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(10.0 / 20.0, context.pop(), 0.000001);
	}
	
	@Test(expected=IllegalStateException.class)
	public void plusOperationShouldThrowWhenLessThanTwoElements() {
		Scanner scanner = new Scanner("10 +");
		postfix.interpret(scanner);
	}
	
	@Test(expected=IllegalStateException.class)
	public void minusOperationShouldThrowWhenLessThanTwoElements() {
		Scanner scanner = new Scanner("10 -");
		postfix.interpret(scanner);
	}
	
	@Test(expected=IllegalStateException.class)
	public void multiplyOperationShouldThrowWhenLessThanTwoElements() {
		Scanner scanner = new Scanner("10 *");
		postfix.interpret(scanner);
	}
	
	@Test(expected=IllegalStateException.class)
	public void divideOperationShouldThrowWhenLessThanTwoElements() {
		Scanner scanner = new Scanner("10 /");
		postfix.interpret(scanner);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void divideByZeroShouldThrowAppropriateException() {
		Scanner scanner = new Scanner("10 0 /");
		postfix.interpret(scanner);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void unsupportedOperationShouldThrowAppropriateException() {
		Scanner scanner = new Scanner("10 0 $");
		postfix.interpret(scanner);
	}
}
