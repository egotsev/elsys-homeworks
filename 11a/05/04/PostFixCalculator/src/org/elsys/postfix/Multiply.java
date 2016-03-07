package org.elsys.postfix;

public class Multiply extends BinaryOperation{
	
	public Multiply() {
		super("*");
	}

	@Override
	public double calc(double v1, double v2) {
		return v1 * v2;
	}

}
