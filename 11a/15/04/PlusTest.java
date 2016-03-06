package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlusTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		
		//setup
		double v1 = 2.0;
		double v2 = 5.0;
				
		//execute
		double res = Plus.eval(v1 + v2);
		
		
		//assert
		double expected = 7.0;
		assertEquals(expected, res);
	
	}
}
