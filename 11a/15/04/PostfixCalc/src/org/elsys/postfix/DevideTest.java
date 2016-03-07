package org.elsys.postfix;

import static org.junit.Assert.*;
import java.util.*;


public class DevideTest {
	private Postfix postfix;
	/*
	@Test
	public void test() {
		//fail("Not yet implemented");
	
		//setup
		double v1 = 4.0;
		double v2 = 2.0;
				
		//execute
		double res = Devide.eval(v1 / v2);
				
		//assert
		double expected = 2.0;
		assertEquals(expected, res);
				
	}
	*/

	public void test() {
		Scanner scanner = new Scanner("10 20 +");
		postfix.interpret(scanner);
		
		Stack<Double> context = postfix.getContext();
		
		assertEquals(1, context.size());
		assertEquals(30.0, context.pop(), 0.000001);
	}
}