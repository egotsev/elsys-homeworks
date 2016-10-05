package org.elsys.postfix;

public class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}
	
	public double calc(double val1, double val2){
		double res = val1 + val2;
		return res;
	}
}
