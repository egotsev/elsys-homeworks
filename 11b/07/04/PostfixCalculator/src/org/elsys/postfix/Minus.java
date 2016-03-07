package org.elsys.postfix;

public class Minus extends BinaryOperation {

	public Minus() {
		// TODO Auto-generated constructor stub
		super("-");
	}

	@Override
	public double calc(double v1, double v2) {
		return v2 - v1;
	}

}
