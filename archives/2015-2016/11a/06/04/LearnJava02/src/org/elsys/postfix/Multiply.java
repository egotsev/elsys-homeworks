package org.elsys.postfix;

public class Multiply extends BinaryOperation {
	
	public Multiply() {
		super("*");
	}

	public double calc(double value1, double value2)
	{
		double result = value1 * value2;
		return result;
	}
}

