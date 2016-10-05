package org.elsys.postfix.operation.binary;

import org.elsys.postfix.operation.BinaryOperation;

public class Plus extends BinaryOperation {
	
	public Plus() {
		super("+");
	}

	@Override
	public double calc(double v1, double v2) {
		return v1 + v2;
	}
}
