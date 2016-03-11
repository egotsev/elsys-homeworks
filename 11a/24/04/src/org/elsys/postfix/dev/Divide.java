package org.elsys.postfix.dev;

public class Divide extends BinaryOperation {

	public Divide() {
		super("/");
	}
	
	public double calc(double v1, double v2) {
		if(v2 == 0) {
			System.out.println("Cannot divide by zero!");
			throw new IllegalArgumentException();
		}
		double res = v1/v2;
		return res;
	}

}
