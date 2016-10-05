package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		
	}

	@Override
	public void eval() {
		Stack<Double> context = getContext();
		double num1 = context.pop();
		double num2 = context.pop();
		double res = calc(num1, num2);
		System.println("R:" + res);
		getContext().push(res);
		
	}

	public abstract double calc(double v1, double v2);
}
