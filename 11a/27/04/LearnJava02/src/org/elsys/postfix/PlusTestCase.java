package org.elsys.postfix;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PlusTestCase {
	
	private Plus operation = null;

	@Before
	public void setUp() throws Exception {
		operation = new Plus("+");
	}


	@After
	public void tearDown() throws Exception {
		operation = null;
	}

	@Test
	public void testOperationPlus() {

		assertEquals(14, operation.calc(7.0,7.0), 0.0000001);
	}
	

}
