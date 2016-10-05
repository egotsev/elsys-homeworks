package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() {
		int size = getContext().size();
		if (size < 2) {
			throw new IllegalStateException();
		}
		double v1 = getContext().pop();
		double v2 = getContext().pop();
		calc(v1, v2);

	}

	public abstract double calc(double v1, double v2);
}
