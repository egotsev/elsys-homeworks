package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DivideTestCase {	
	@Test
	public void testCalc() {
		Divide divideOperation = new Divide();
		
		double res = divideOperation.calc(20.0, 2.0);
		
		assertEquals(10.0, res, 0.000001);
	}
}