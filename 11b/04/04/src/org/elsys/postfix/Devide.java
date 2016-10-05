package org.elsys.postfix;

public class Devide extends BinaryOperation{
	
	public Devide() {
		super("+");
	}
	
	@Override
	public double calc(double v1, double v2) {
		if(v2 != 0){
			return v1/v2;
		}
		throw new IllegalStateException();
	}
}
