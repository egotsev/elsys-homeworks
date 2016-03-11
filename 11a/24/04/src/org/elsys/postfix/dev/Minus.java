package org.elsys.postfix.dev;

public class Minus extends BinaryOperation {

	public Minus() {
		super("-");
	}
	
	public double calc(double v1, double v2) {
		double res = v1 - v2;
		return res;
	}

}
