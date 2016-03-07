package org.elsys.homework4;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDevide {

	@Test
	public void DevideCalc() {
		Devide devide = new Devide();
		assertEquals(2.0 , devide.calc(4.0 , 2.0), 0.000001);
	}

}
