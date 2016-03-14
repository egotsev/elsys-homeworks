package org.elsys.postfix;

public class Multiply extends BinaryOperation{
	
	public Multiply() {
		super("*");
	}

	@Override
	public double calc(double a, double b){
		double result = a * b;
		return result;
	}

}
