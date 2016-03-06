package org.elsys.postfix.dev;

public class Plus extends BinaryOperation {

	public Plus() {
		super("+");
	}
	
	public double calc(double v1, double v2) {
		double res = v1 + v2;
		return res;
	}
	
}
