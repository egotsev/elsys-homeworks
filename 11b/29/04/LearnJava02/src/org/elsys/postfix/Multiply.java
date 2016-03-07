package org.elsys.postfix;

public class Multiply extends BinaryOperation{
	
	public Multiply() {
		super("*");
	}
	
	public double calc(double val1, double val2){
		double res = val1 * val2;
		return res;
	}
}

