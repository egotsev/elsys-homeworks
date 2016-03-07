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

		double v1 = getContext().pop();
		double v2 = getContext().pop();

		double result = calc(v2, v1);
		this.getContext().push(result);
	}

	public abstract double calc(double v1, double v2);
}
