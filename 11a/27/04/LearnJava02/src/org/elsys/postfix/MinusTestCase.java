package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MinusTestCase {

	private Minus operation = null;

	@Before
	public void setUp() throws Exception {
		operation = new Minus("-");
	}


	@After
	public void tearDown() throws Exception {
		operation = null;
	}

	@Test
	public void testOperationMinus() {		
		assertEquals(3, operation.calc(10,7.0), 0.0000001);
	}

}
