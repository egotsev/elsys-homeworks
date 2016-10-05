package org.elsys.postfix.operation.binary;

import org.elsys.postfix.operation.BinaryOperation;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class Minus extends BinaryOperation {

    public Minus() {
        super("-");
    }

    @Override
    public double calc(double v1, double v2) {
        return v2 - v1;
    }
}
