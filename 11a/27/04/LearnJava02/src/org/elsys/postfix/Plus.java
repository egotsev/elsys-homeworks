package org.elsys.postfix;

public class Plus extends BinaryOperation {
	String operation = "+";
	double result;
	public Plus(String operation) {
		super(operation);
	}

	@Override
	public double calc(double v1, double v2) {
		return v1 + v2;
	}

}
