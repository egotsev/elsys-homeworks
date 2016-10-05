package org.elsys.postfix;

import static org.junit.Assert.*;

import org.junit.Test;

public class Junit {

	@Test
	public void calcMinus() {
		Minus a = new Minus();
		double rslt = a.calc(1.00, 0.5);
		assertEquals(0.5, rslt,0.000001);
	}

	@Test
	public void calcPlus() {
		Plus a = new Plus();
		double reslt = a.calc(1.00, 0.5);
		assertEquals(1.5, reslt,0.000001);
	}
	@Test
	public void calcDevide() {
		Devide a = new Devide();
		double reslt = a.calc(2.00, 2);
		assertEquals(1.0, reslt,0.000001);
	}
	@Test
	public void calcMultiply() {
		 Multiply a = new Multiply();
		double reslt = a.calc(1.00, 2);
		assertEquals(2, reslt,0.000001);
	}
}
