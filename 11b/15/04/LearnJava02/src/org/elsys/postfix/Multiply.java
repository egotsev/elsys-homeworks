package org.elsys.postfix;

public class Multiply extends BinaryOperation{
	
	public Multiply() {
		super("*");
	}

	@Override
	public double calc(double v1, double v2) {
		
		double res = v1 * v2;
		System.out.println("R: " + res);
		getContext().push(res);
		return res;
	}

}
