package org.elsys.postfix.operation.binary;

import org.elsys.postfix.DivisionByZeroException;
import org.elsys.postfix.UnknownOperationException;
import org.elsys.postfix.operation.BinaryOperation;

/**
 * TUES 15/16 OOP Assignment 4
 * Created by plamen on 3/6/16.
 */
public class Divide extends BinaryOperation {
    public Divide() {
        super("/");
    }

    @Override
    public double calc(double v1, double v2) throws DivisionByZeroException {
        if (v1 != 0) {
            return v2 / v1;
        }
        throw new DivisionByZeroException();
    }
}
