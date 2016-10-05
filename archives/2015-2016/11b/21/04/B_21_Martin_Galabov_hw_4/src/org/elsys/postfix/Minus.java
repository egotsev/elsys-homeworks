package org.elsys.postfix;

public class Minus extends BinaryOperation {
	
	public Minus() {
		super("-");
	}
	
	public void calc (double v1, double v2){
		double res = v2 - v1;
		System.out.println("R: "+res);
		getContext().push(res);
	}
}
