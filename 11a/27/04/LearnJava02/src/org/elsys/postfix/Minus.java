package org.elsys.postfix;

public class Minus extends BinaryOperation {
	String operation = "-";
	public Minus(String operation) {
		super (operation);
	}

	@Override
	public double calc(double v1, double v2) {
		return v1 - v2;
	}
}
