package org.elsys.postfix;

public class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}

	@Override
	public void calc(double v1, double v2) {		
		double res = v1 + v2;
		System.out.println("R: " + res);
		
		getContext().push(res);
	}

}
