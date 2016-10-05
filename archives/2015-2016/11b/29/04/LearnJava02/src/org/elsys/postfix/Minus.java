package org.elsys.postfix;

public class Minus extends BinaryOperation{
	
	public Minus() {
		super("-");
	}
	
	public double calc(double val1, double val2){
		double res = val2 - val1;
		return res;
	}
}
