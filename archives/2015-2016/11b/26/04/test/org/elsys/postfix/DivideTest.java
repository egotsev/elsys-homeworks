package org.elsys.postfix;

import org.elsys.postfix.operation.binary.Divide;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class DivideTest {
    @Test
    public void shouldCalculateCorrectly() throws Exception {
        Divide divide = new Divide();
        assertEquals(1.5, divide.calc(2, 3), 0.000001);
    }
}
