package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultiplyTestCase {	
	@Test
	public void testCalc() {
		Multiply multiplyOperation = new Multiply();
		
		double res = multiplyOperation.calc(20.0, 2.0);
		
		assertEquals(40.0, res, 0.000001);
	}
}