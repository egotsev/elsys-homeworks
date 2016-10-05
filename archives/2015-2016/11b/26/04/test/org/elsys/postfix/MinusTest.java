package org.elsys.postfix;

import org.elsys.postfix.operation.binary.Minus;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class MinusTest {
    @Test
    public void shouldCalculateCorrectly() throws Exception {
        Minus minus = new Minus();
        assertEquals(3.0, minus.calc(2, 5), 0.000001);
    }
}
