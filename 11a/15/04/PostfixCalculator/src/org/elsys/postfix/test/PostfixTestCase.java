
package org.elsys.postfix.test;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

import org.elsys.postfix.*;
import org.junit.*;


public class PostfixTestCase {

	private Postfix postfix;
	
	@Before
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Plus());
		postfix.addOperation(new Minus());
		postfix.addOperation(new Multiply());
		postfix.addOperation(new Devide());
	}

	@Test
	public void testSubtraction() {
		Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-10.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testPlusClass() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test
	public void testMultiplyClass() {
		Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
	@Test
	public void testDevideClass() {
		Scanner scanner = new Scanner("20 4 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(5.0, context.pop(), 0.000001);
	}
	@Test
	public void simpleTestEquation(){
		Scanner scanner = new Scanner("5 6 + 4 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(7.0, context.pop(), 0.000001);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testUnknownOperator(){
			Scanner scanner = new Scanner("5 6 k");
			postfix.interpret(scanner);
	}
}