package org.elsys.postfix;

public class Divide extends BinaryOperation {
	String operation = "/";
	public Divide(String operation) {
		super(operation);
	}
	@Override
	public double calc(double v1, double v2) {
		if (v2 == 0) {
			throw new IllegalArgumentException();
		}
		return v1 / v2;
	}

}
