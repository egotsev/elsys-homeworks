package org.elsys.postfix.operation;

import org.elsys.postfix.DivisionByZeroException;
import org.elsys.postfix.operation.Operation;

import java.util.Stack;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() throws DivisionByZeroException {
        Stack<Double> context = getContext();

		if (context.size() >= 2) {
			double result = calc(context.pop(), context.pop());
            context.push(result);
		} else {
            throw new IllegalStateException();
        }
	}

	public abstract double calc(double v1, double v2) throws DivisionByZeroException;
}
