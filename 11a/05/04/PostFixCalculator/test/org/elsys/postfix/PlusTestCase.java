package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlusTestCase {

	private Stack<Double> testValues = new Stack<Double>();
	Plus plus = new Plus();
	
	@Before
	public void setUp() throws Exception {
		plus.setContext(testValues);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		testValues.push(10.0);
		testValues.push(40.0);
		plus.eval();
		
		assertEquals(1, plus.getContext().size());
		assertEquals(50.0, plus.getContext().pop(), 0.000001);
	}

	@Test(expected=IllegalStateException.class)
	public void testNumberOfArguments() {
		testValues.push(10.0);
		plus.eval();
	}
}