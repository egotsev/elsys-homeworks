package org.elsys.postfix.operation.binary;

import org.elsys.postfix.operation.BinaryOperation;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class Multiply extends BinaryOperation {
    public Multiply() {
        super("*");
    }

    @Override
    public double calc(double v1, double v2) {
        return v1 * v2;
    }
}
