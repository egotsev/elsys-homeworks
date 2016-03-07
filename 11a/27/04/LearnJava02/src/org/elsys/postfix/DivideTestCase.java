package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DivideTestCase {

	private Divide operation = null;

	@Before
	public void setUp() throws Exception {
		operation = new Divide("/");
	}


	@After
	public void tearDown() throws Exception {
		operation = null;
	}

	@Test
	public void testOperationDivide() throws IllegalArgumentException {

		assertEquals(20, operation.calc(40.0,2.0), 0.0000001);
		assertEquals(5, operation.calc(25.0,5.0), 0.0000001);
		assertEquals((-4), operation.calc(20.0,(-5.0)), 0.0000001);
	}
}
