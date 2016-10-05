package org.elsys.homework4;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMultiply {

	@Test
	public void Multiply() {
		Multiply multi = new Multiply();
		assertEquals(5.0, multi.calc(2.5, 2.0), 0.000001);
	}

}
