package org.elsys.postfix.dev;

public class Multiply extends BinaryOperation {

	public Multiply() {
		super("*");
	}
	
	public double calc(double v1, double v2) {
		double res = v1*v2;
		return res;
	}

}
