package org.elsys.postfix;

public class Divide extends BinaryOperation{
	public Divide() {
		super("/");
	}
	
	public void calc(double v1, double v2){
		if(v1 == 0){
			throw new IllegalArgumentException("Cannot divide by zero...");
		}
		double res = v2/v1;
		System.out.println("R: "+res);
		getContext().push(res);
	}
}
