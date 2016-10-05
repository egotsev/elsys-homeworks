package org.elsys.postfix;

import org.elsys.postfix.operation.binary.Divide;
import org.elsys.postfix.operation.binary.Minus;
import org.elsys.postfix.operation.binary.Multiply;
import org.elsys.postfix.operation.binary.Plus;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class PostfixTest {
    Postfix postfix;

    @Before
    public void setUp() throws Exception {
        postfix = new Postfix();
        postfix.addOperation(new Plus());
        postfix.addOperation(new Minus());
        postfix.addOperation(new Multiply());
        postfix.addOperation(new Divide());
    }

    @Test(expected = UnknownOperationException.class)
    public void shouldRefuseUnknownOperation() throws Exception {
        Scanner sc = new Scanner("1 2 _");
        postfix.interpret(sc);
    }

    @Test(expected = DivisionByZeroException.class)
    public void shouldNotDivideByZero() throws Exception {
        Scanner sc = new Scanner("2 0 /");
        postfix.interpret(sc);
    }

    @Test(expected = IllegalStateException.class)
    public void binaryOperationShouldCheckArgumentSize() throws Exception {
        Scanner sc = new Scanner("2 +");
        postfix.interpret(sc);
    }
}
