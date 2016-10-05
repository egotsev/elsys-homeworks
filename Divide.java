package org.elsys.postfix;

public class Divide extends BinaryOperation{

	public Divide() {
		super("/");
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calc(double v1, double v2) {
		// TODO Auto-generated method stub
		double res;
		if (v1 == 0) {
			throw new IllegalArgumentException("Argument 'divisor' is 0");
		}
		res = v2/v1; 
		return res;
	}
	
}
