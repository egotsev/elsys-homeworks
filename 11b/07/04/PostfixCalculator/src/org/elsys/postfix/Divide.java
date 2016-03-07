package org.elsys.postfix;

public class Divide extends BinaryOperation {

	public Divide() {
		// TODO Auto-generated constructor stub
		super("/");
	}

	@Override
	public double calc(double v1, double v2) {
		if(v1 != 0) {
			return v2/v1;
		}else{
			throw new ArithmeticException("Can not divide by zero");
		}
	}
}
