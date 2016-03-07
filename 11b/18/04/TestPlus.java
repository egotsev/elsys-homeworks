package org.elsys.homework4;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPlus {

	@Test
	public void PlusCalc() {
		Plus plus = new Plus();
		assertEquals(3.4, plus.calc(2.1, 1.2), 0.000001);
	}

}
