package org.elsys.postfix;

public class Multiply extends BinaryOperation {

	public Multiply() {
		// TODO Auto-generated constructor stub
		super("*");
	}

	@Override
	public double calc(double v1, double v2) {
		return v1 * v2;
	}
}
