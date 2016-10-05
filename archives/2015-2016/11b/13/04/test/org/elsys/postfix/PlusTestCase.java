package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlusTestCase {	
	@Test
	public void testCalc() {
		Plus plusOperation = new Plus();
		
		double res = plusOperation.calc(25.0, 10.0);
		
		assertEquals(35.0, res, 0.000001);
	}
}