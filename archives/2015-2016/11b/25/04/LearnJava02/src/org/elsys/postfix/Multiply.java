package org.elsys.postfix;

public class Multiply extends BinaryOperation {
	public Multiply(){
		super("*");
	}
	
	@Override
	public double calc(double firstParam, double secondParam) {
		return firstParam * secondParam;
	}
	
}
