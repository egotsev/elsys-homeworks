package org.elsys.postfix.dev;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DivideUnitTest extends PostfixTestCase {

	@Before
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Divide());
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void test() {
		Scanner scanner = new Scanner("5 35 /");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(0.142,context.lastElement(),0.001);
	}

}
