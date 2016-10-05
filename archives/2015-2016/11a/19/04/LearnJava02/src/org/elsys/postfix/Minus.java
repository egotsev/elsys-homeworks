package org.elsys.postfix;

public class Minus extends BinaryOperation{
	public Minus() {
		super("-");
	}

	@Override
	public double calc(double a, double b){
		double result = b - a;
		return result;
	}
}
