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

	/*
	@After
	public void tearDown() throws Exception {
	}
	*/

	@Test (timeout=1000)
	public void testAddition() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test (timeout=1000)
	public void testSubtraction() {
		Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-10.0, context.pop(), 0.000001);
	}
	
	@Test (timeout=1000)
	public void testMultiplication() {
		Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
	@Test (timeout=1000)
	public void testDevision() {
		Scanner scanner = new Scanner("20 4 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(5.0, context.pop(), 0.000001);
	}
	@Test (timeout = 1000)
	public void testEquasion(){
		Scanner scanner = new Scanner("5 0.4 1.5 + * -6 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-57.0, context.pop(), 0.000001);
	}
	@Test(timeout = 1000, expected = IllegalStateException.class)
	public void testIllegalStateException(){
			Scanner scanner = new Scanner("5 +");
			postfix.interpret(scanner);
		
	}
	@Test(timeout = 1000, expected = UnsupportedOperationException.class)
	public void testUnknownOperator(){
			Scanner scanner = new Scanner("5 6 k");
			postfix.interpret(scanner);
	}
	
	
	
}
