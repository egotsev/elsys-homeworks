package org.elsys.postfix;

public class Divide extends BinaryOperation{
	
	public Divide() {
		super("/");
	}
	
	@Override
	public double calc(double v1, double v2) {
		if (v2 == 0) {
			throw new IllegalArgumentException();
		}
		
		return (v1 / v2);
	}
}
