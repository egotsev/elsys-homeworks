/*package org.elsys.postfix;

import static org.junit.Assert.*;

import java.util.Scanner;
import java.util.Stack;

import org.junit.Test;

public class PostfixTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		
		Postfix postfix = new Postfix();
	    postfix.addOperation(new Plus());
	    Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	    
	    Postfix postfix = new Postfix();
	    postfix.addOperation(new Minus());
	    Scanner scanner = new Scanner("10 20 -");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(-10.0, context.pop(), 0.000001);
	    
	    
	    Postfix postfix = new Postfix();
	    postfix.addOperation(new Devide());
	    Scanner scanner = new Scanner("10 20 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(0.5, context.pop(), 0.000001);
	    
	    Postfix postfix = new Postfix();
	    postfix.addOperation(new Multiply());
	    Scanner scanner = new Scanner("10 20 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(200.0, context.pop(), 0.000001);
	}
}*/