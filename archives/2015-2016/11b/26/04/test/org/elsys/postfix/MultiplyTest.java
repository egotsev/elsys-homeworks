package org.elsys.postfix;

import org.elsys.postfix.operation.binary.Multiply;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class MultiplyTest {
    @Test
    public void shouldCalculateCorrectly() throws Exception {
        Multiply multiply = new Multiply();
        assertEquals(6, multiply.calc(2, 3), 0.000001);
    }
}
