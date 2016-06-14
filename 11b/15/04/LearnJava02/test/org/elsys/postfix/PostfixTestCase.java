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
	public void test() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
	
	@Test(expected = Exception.class)
	public void testBinaryOperation()
	{
		Scanner scanner = new Scanner("10 +");
		postfix.interpret(scanner);
	}
	
	@Test
	public void testPlus()
	{
		Scanner scanner = new Scanner("10 50 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(60.0, context.lastElement(), 0.000001);
		
	}
	
	@Test
	public void testMinus()
	{
		Scanner scanner = new Scanner("50 10 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(40.0, context.lastElement(), 0.000001);
		
	}
	
	@Test
	public void testMultiply()
	{
		Scanner scanner = new Scanner("10 50 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(500.0, context.lastElement(), 0.000001);
		
	}
	
	@Test
	public void testDivision()
	{
		Scanner scanner = new Scanner("50 10 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(5.0, context.lastElement(), 0.000001);
		
	}
	
	@Test(expected = Exception.class)
	public void testDivisionByZero()
	{
		Scanner scanner = new Scanner("50 0 /");
		postfix.interpret(scanner);
	}
	
	@Test
	public void testInvalidOperation()
	{
		Scanner scanner = new Scanner("50 10 |");
		postfix.interpret(scanner);
	}
	
	
	
	
	
	
	
	
	
	

}
