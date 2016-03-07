package org.elsys.postfix;

import org.elsys.postfix.operation.BinaryOperation;
import org.elsys.postfix.operation.binary.Plus;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static junit.framework.TestCase.assertEquals;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class BinaryOperationTest {
    BinaryOperation binaryOperation;

    @Before
    public void setUp() throws Exception {
        binaryOperation = new Plus();
    }

    @Test(expected = IllegalStateException.class)
    public void evalShouldRefuseEmptyContext() throws Exception {
        Stack<Double> emptyContext = new Stack<>();
        binaryOperation.setContext(emptyContext);
        binaryOperation.eval();
    }

    @Test(expected = IllegalStateException.class)
    public void evalShouldRefuseSingularContext() throws Exception {
        Stack<Double> singularContext = new Stack<>();
        singularContext.add(3.14);
        binaryOperation.setContext(singularContext);
        binaryOperation.eval();
    }

    @Test
    public void evalShouldAcceptValidContext() throws Exception {
        Stack<Double> context = new Stack<>();

        context.add(1.23);
        context.add(4.56);
        context.add(7.89);

        binaryOperation.setContext(context);
        binaryOperation.eval();

        double top = binaryOperation.getContext().peek();
        assertEquals(top, 4.56 + 7.89);
    }
}
