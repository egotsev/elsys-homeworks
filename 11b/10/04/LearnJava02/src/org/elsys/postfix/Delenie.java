package org.elsys.postfix;

public class Del extends BinaryOperation{
	
	public Del() {
		super("/");
	}

	@Override
	public double calc(double v1, double v2) {
		return v2 / v1;
	}
}