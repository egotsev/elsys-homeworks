package org.elsys.postfix;

public class Minus extends BinaryOperation {

	public Minus() {
		super("-");
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calc(double v1, double v2) {
		// TODO Auto-generated method stub
		return v2- v1;
	}
	
}
