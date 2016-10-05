package org.elsys.postfix.test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.elsys.postfix.Devide;
import org.elsys.postfix.Multiply;
import org.junit.Before;
import org.junit.Test;

public class DevideTestCase extends Devide {
	
	DevideTestCase DevideTestCase;
	
	@Before
	public void setUp() {
		this.DevideTestCase = this;
	}
	
	@Test
	public void Devidetest() {
		//fail("Not yet implemented");
		Stack<Double> st = new Stack<Double>();
		st.push(1.0);
		st.push(2.0);
		this.DevideTestCase.setContext(st);
		DevideTestCase.eval();
		Stack<Double> res = DevideTestCase.getContext();
		assertEquals(0.5, res.pop(), 0.000001);
	}
}