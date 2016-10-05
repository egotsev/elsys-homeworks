package org.elsys.homework4;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMinus {

	@Test
	public void MinusCalc() {
		Minus minus = new Minus();
		assertEquals(4.5, minus.calc(9.0, 4.5), 0.000001);
	}
}
