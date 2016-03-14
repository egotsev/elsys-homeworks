package org.elsys.postfix;

import org.junit.*;

import static org.junit.Assert.assertEquals;

import java.util.*;

public class BinaryOperationTestMultiplication extends Multiply{
	BinaryOperation testInstance;
	
	@Before 
	public void setUp(){
		this.testInstance = this;
		
	}
	
	@Test
	public void testPlus(){
		Stack<Double> stack = new Stack<Double>();
		stack.push(1.5);
		stack.push(2.5);
		this.testInstance.setContext(stack);
		testInstance.eval();
		Stack<Double> result = testInstance.getContext();
		assertEquals(1, result.size());
		assertEquals(3.75, result.pop(), 0.000001);
	}
}
