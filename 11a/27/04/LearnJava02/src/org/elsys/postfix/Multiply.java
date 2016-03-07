package org.elsys.postfix;

public class Multiply extends BinaryOperation {
	String operation = "*";
	public Multiply(String operation) {
		super(operation);
	}

	@Override
	public double calc(double v1, double v2) {
		return v1*v2;
	}
}