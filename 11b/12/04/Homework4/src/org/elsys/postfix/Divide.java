package org.elsys.postfix;

public class Divide extends BinaryOperation{
	
	public Divide() {
		super("/");
	}

	@Override
	public double calc(double v1, double v2) {
		if(v1 == 0){
			throw new IllegalStateException();
		}
		return v2 / v1;
	}
}