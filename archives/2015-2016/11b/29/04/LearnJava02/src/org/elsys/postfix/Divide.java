package org.elsys.postfix;

public class Divide extends BinaryOperation{
	
	public Divide() {
		super("/");
	}
	
	public double calc(double val1, double val2){
		double res = val2 / val1;
		return res;
	}
}
