package org.elsys.postfix;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
		postfix = null;
	}

	@Test
	public void StackTest() {
		Scanner scanner;
		Stack<Double> context;
		
		scanner = new Scanner("10 20 +");
	    postfix.interpret(scanner);
	    context = postfix.getContext();
		assertEquals("30.0", context.pop().toString());
		
		
		scanner = new Scanner("100 20 -");
	    postfix.interpret(scanner);
	    context = postfix.getContext();
		assertEquals("80.0", context.pop().toString());
		
		scanner = new Scanner("100 100 *");
	    postfix.interpret(scanner);
	    context = postfix.getContext();
		assertEquals("10000.0", context.pop().toString());
		
		scanner = new Scanner("1000 2000 /");
	    postfix.interpret(scanner);
	    context = postfix.getContext();
		assertEquals("0.5", context.pop().toString());
	}
	
	@Test(expected=ArithmeticException.class)
	public void testZeroDivision() {
		Scanner scanner;
		
		scanner = new Scanner("1 0 /");
	    postfix.interpret(scanner);
	    postfix.getContext();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testArguements() {
		Scanner scanner;
		
		scanner = new Scanner("500 /");
	    postfix.interpret(scanner);
	}
	
	@Test
	public void testOperation() {
		Scanner scanner;
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
		
		scanner = new Scanner("10 10 ^");
	    postfix.interpret(scanner);
	    assertEquals("D: <10.0>\nD: <10.0>\nUnknown operation <^>\n", outContent.toString());
	}

}
