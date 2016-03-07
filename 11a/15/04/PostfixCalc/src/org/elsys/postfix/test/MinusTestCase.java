package org.elsys.postfix.test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.elsys.postfix.Minus;
import org.elsys.postfix.Plus;
import org.junit.Before;
import org.junit.Test;

public class MinusTestCase extends Minus {
	
	MinusTestCase MinusTestCase;
	
	
	@Before
	public void setUp() {
		this.MinusTestCase = this;
	}
	
	
	@Test
	public void Minustest() {
		//fail("Not yet implemented");
		Stack<Double> st = new Stack<Double>();
		st.push(1.0);
		st.push(2.0);
		this.MinusTestCase.setContext(st);
		MinusTestCase.eval();
		Stack<Double> res = MinusTestCase.getContext();
		assertEquals(-1.0, res.pop(), 0.000001);
	}
}