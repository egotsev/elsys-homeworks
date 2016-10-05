package org.elsys.postfix.test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.elsys.postfix.BinaryOperation;
import org.elsys.postfix.Plus;
import org.junit.Before;
import org.junit.Test;

public class PlusTestCase extends Plus {
	
	PlusTestCase PlusTestCase;
	
	@Before
	public void setUp() {
		this.PlusTestCase = this;
	}
		
	@Test
	public void Plustest() {
		//fail("Not yet implemented");
		Stack<Double> st = new Stack<Double>();
		st.push(1.0);
		st.push(2.0);
		this.PlusTestCase.setContext(st);
		PlusTestCase.eval();
		Stack<Double> res = PlusTestCase.getContext();
		assertEquals(3.0, res.pop(), 0.000001);
	}
}