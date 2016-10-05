package org.elsys.postfix;

public class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}

	@Override
	public double calc(double v1, double v2) {
		double res;
		// TODO Auto-generated method stub
		res = v1 + v2;
		return res;
	}
	
}
