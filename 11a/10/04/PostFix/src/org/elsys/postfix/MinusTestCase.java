package org.elsys.postfix;

import static org.junit.Assert.*;

import java.util.Scanner;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class MinusTestCase extends PostfixTestCase
{

	@Override
	@Before
	public void setUp() throws Exception {
		postfix = new Postfix();
		postfix.addOperation(new Minus());
		
		
	}

	@Override
	@Test
	public void test() {
		Scanner scanner = new Scanner("10 5 -");
		postfix.interpret(scanner);
		Stack<Double> context = postfix.getContext();
		assertEquals(5,context.lastElement(),0.01);
		assertEquals(1,context.size());
	}
	

}
