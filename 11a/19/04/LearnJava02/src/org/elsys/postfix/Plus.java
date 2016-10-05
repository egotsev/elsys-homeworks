package org.elsys.postfix;

public class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}

	@Override
	public double calc(double a, double b){
		double result = a + b;
		return result;
	}

}
