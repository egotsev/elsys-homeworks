package org.elsys.postfix;

public class Umn extends BinaryOperation{
	
	public Umn() {
		super("*");
	}

	@Override
	public double calc(double v1, double v2) {
		return v2 * v1;
	}
}