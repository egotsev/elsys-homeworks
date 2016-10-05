package org.elsys.postfix;

public class Minus extends BinaryOperation {
	
	public Minus() {
		super("-");
	}

	public double calc(double value1, double value2)
	{
		double result = value1 - value2;
		return result;
	}
}