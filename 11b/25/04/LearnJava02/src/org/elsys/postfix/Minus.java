package org.elsys.postfix;

public class Minus extends BinaryOperation {
	public Minus() {
		super("-");
	}
	@Override
	public double calc(double firstParam, double secondParam) {
		return firstParam - secondParam;
	}
}
