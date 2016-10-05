package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		if (this.getContext().size() < 2) {
			throw new IllegalStateException();
		}
		
		double firstValue = this.getContext().pop();
		double secondValue = this.getContext().pop();
		
		double result = calc(secondValue, firstValue);

		this.getContext().push(result);
		System.out.println("R: " + result);
	}

	public abstract double calc(double v1, double v2);
}
