package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.Test;

public class DevideTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	
		//setup
		double v1 = 4.0;
		double v2 = 2.0;
				
		//execute
		double res = Devide.evil(v1 / v2);
				
		//assert
		double expected = 2.0;
		assertEquals(expected, res);
				
	}
}