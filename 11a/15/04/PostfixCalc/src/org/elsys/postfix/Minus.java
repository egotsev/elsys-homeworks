package org.elsys.postfix;

public class Minus extends Operation {
	public Minus() {
		super("-");
	}
	
	@Override
	public void eval() {
		double v1 = getContext().pop();
		double v2 = getContext().pop();
		
		double res = v2 - v1;
		System.out.println("R: " + res);
		getContext().push(res);
	}
}