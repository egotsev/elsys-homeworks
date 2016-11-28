package org.elsys.postfix;

public class Minus extends BinaryOperation{
	
	public Minus() {
		super("-");
	}

	@Override
	public double calc(double v1, double v2) {
		return v1 - v2;
	}

}
