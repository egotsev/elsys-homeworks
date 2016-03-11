package org.elsys.postfix.dev;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;
import java.util.Stack;

public class PlustUnitTest extends PostfixTestCase {
	
	@Before
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Plus());
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void test() {
		Scanner scanner = new Scanner("5.4 6.2 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(11.6,context.lastElement(),0.0001);
	}
	
}
