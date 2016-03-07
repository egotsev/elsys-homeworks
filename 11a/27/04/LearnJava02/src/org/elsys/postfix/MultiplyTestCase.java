package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MultiplyTestCase {

	private Multiply operation = null;

	@Before
	public void setUp() throws Exception {
		operation = new Multiply("*");
	}


	@After
	public void tearDown() throws Exception {
		operation = null;
	}

	@Test
	public void testOperationMultiply() {

		assertEquals(49, operation.calc(7.0,7.0), 0.0000001);
		assertEquals(20, operation.calc(2.0,10.0), 0.0000001);
		assertEquals((-20), operation.calc(2.0,(-10.0)), 0.0000001);
		assertEquals(10, operation.calc((-2.0),(-5.0)), 0.0000001);
	}

}
