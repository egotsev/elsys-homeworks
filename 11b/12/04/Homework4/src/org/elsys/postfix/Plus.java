package org.elsys.postfix;

public class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}

	@Override
	public double calc(double v1, double v2) {
		return v2 + v1;
	}
}