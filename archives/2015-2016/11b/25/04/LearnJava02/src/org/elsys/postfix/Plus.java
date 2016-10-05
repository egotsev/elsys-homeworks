package org.elsys.postfix;

public class Plus extends BinaryOperation {
	
	public Plus() {
		super("+");
	}

	@Override
	public double calc(double firstParam, double secondParam) {
		return firstParam + secondParam;
	}

}
