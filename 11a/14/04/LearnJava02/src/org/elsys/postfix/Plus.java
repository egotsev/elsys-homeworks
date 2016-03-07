package org.elsys.postfix;

public abstract class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}

	public void calc() {
		double v1 = getContext().pop();
		double v2 = getContext().pop();
		
		double res = v1 + v2;
		System.out.println("R: " + res);
		getContext().push(res);
	}

}
