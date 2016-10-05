package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		
		double v1 = getContext().pop();
		double v2 = getContext().pop();
		
		double result = calc(v1, v2);
		
		System.out.println("Result: " + result);
		getContext().push(result);
	}

	public abstract double calc(double v1, double v2);
}
