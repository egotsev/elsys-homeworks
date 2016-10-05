package org.elsys.postfix;

public class Plus extends BinaryOperation
{
	
	public Plus() {
		super("+");
	}

	public double calc(double v1, double v2) {
		double v1 = getContext().pop();
		double v2 = getContext().pop();
		
		double res = v1 + v2;
		System.out.println("R: " + res);
		getContext().push(res);
	}
	
	

}
