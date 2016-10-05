package org.elsys.postfix.test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.elsys.postfix.Multiply;
import org.elsys.postfix.Plus;
import org.junit.Before;
import org.junit.Test;

public class MultiplyTestCase extends Multiply {
	
	MultiplyTestCase MultiplyTestCase;
	
	@Before
	public void setUp() {
		this.MultiplyTestCase = this;
	}
	
	@Test
	public void Multiplytest() {
		//fail("Not yet implemented");
		Stack<Double> st = new Stack<Double>();
		st.push(1.0);
		st.push(2.0);
		this.MultiplyTestCase.setContext(st);
		MultiplyTestCase.eval();
		Stack<Double> res = MultiplyTestCase.getContext();
		assertEquals(2.0, res.pop(), 0.000001);
	}
}