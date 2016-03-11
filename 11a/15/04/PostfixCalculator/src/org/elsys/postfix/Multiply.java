package org.elsys.postfix;

public class Multiply extends Operation {
	public Multiply() {
		super("*");
	}
	
	@Override
	public void eval() {
		double v1 = getContext().pop();
		double v2 = getContext().pop();
		
		double res = v1 * v2;
		System.out.println("R: " + res);
		getContext().push(res);
	}
}