package org.elsys.postfix;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PostfixTest {
	Postfix postfix;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	@Before
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Plus());
		postfix.addOperation(new Minus());
		postfix.addOperation(new Divide());
		postfix.addOperation(new Multiply());
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
		postfix = null;
		System.setOut(null);
	}

	@Test
	public void testPlus() {
		Scanner scanner = new Scanner("14 9 + 15 10 + +");
	    postfix.interpret(scanner);
	    Stack<Double> context = postfix.getContext();
	    assertEquals(48.0, context.elementAt(0), 0.1);
	}
	
	@Test
	public void testMinus() {
		Scanner scanner = new Scanner("55 9 - 48 10 - -");
	    postfix.interpret(scanner);
	    Stack<Double> context = postfix.getContext();
	    assertEquals(8.0, context.elementAt(0), 0.1);
	}
	
	@Test
	public void testDivide() {
		Scanner scanner = new Scanner("225 15 / 5 /");
	    postfix.interpret(scanner);
	    Stack<Double> context = postfix.getContext();
	    assertEquals(3.0, context.elementAt(0), 0.1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testDivideByZero() {
		Scanner scanner = new Scanner("15 0 /");
	    postfix.interpret(scanner);
	    Stack<Double> context = postfix.getContext();
	    assertEquals(48.0, context.elementAt(0), 0.1);
	}
	
	@Test
	public void testMultiply(){
		Scanner scanner = new Scanner("3 2 * 6 *");
	    postfix.interpret(scanner);
	    Stack<Double> context = postfix.getContext();
	    assertEquals(36.0, context.elementAt(0), 0.1);
	}

	@Test (expected=IllegalStateException.class)
	public void testPlusWithOneArgument(){
		Scanner scanner = new Scanner("13 +");
		postfix.interpret(scanner);
	}
	
	@Test (expected=IllegalStateException.class)
	public void testMinusWithOneArgument(){
		Scanner scanner = new Scanner("13 -");
		postfix.interpret(scanner);
	}
	
	@Test (expected=IllegalStateException.class)
	public void testDivideWithOneArgument(){
		Scanner scanner = new Scanner("13 /");
		postfix.interpret(scanner);
	}
	
	@Test (expected=IllegalStateException.class)
	public void testMultiplyWithOneArgument(){
		Scanner scanner = new Scanner("13 *");
		postfix.interpret(scanner);
	}
}
