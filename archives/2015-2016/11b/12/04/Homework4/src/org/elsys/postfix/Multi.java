package org.elsys.postfix;

public class Multi extends BinaryOperation{
	
	public Multi() {
		super("*");
	}

	@Override
	public double calc(double v1, double v2) {
		return v2 * v1;
	}
}