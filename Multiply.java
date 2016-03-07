package org.elsys.postfix;

public class Multiply extends BinaryOperation{

	public Multiply() {
		super("*");
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calc(double v1, double v2) {
		// TODO Auto-generated method stub
		return v1 * v2;
	}

}
