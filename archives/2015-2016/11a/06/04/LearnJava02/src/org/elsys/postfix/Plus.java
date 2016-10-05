package org.elsys.postfix;

public class Plus extends BinaryOperation {
	
	public Plus() {
		super("+");
	}

	public double calc(double value1, double value2)
	{
		double result = value1 + value2;
		return result;
	}
}
