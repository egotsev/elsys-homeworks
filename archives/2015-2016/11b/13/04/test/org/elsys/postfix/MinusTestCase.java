package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MinusTestCase {	
	@Test
	public void testCalc() {
		Minus minusOperation = new Minus();
		
		double res = minusOperation.calc(25.0, 10.0);
		
		assertEquals(15.0, res, 0.000001);
	}
}