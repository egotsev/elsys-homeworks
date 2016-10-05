package org.elsys.postfix;

import org.elsys.postfix.operation.binary.Plus;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class PlusTest {
    @Test
    public void shouldCalculateCorrectly() throws Exception {
        Plus plus = new Plus();
        assertEquals(3.0, plus.calc(1, 2), 0.000001);
    }
}
