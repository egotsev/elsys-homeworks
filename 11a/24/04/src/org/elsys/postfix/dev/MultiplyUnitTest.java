package org.elsys.postfix.dev;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MultiplyUnitTest extends PostfixTestCase {

	@Before
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Multiply());
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void test() {
		Scanner scanner = new Scanner("4 2.5 *");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(10,context.lastElement(),0.1);
	}

}
