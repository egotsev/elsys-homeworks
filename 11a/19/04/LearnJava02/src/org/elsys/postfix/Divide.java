package org.elsys.postfix;

public class Divide extends BinaryOperation{
	
	public Divide() {
		super("/");
	}

	@Override
	public double calc(double a, double b){
		double result = 0;
		if(a != 0){
			result = b / a;
		}else{
			throw new IllegalArgumentException("Argument 'divisor' is 0");
		}
		return result;
	}

}
