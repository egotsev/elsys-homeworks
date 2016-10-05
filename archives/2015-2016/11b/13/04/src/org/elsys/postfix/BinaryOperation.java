package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {
	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() {
		if (super.getContext().size() < 2) {
			throw new IllegalStateException("A binary operation requires at least two operands.");
		}

		double v1 = super.getContext().pop();
		double v2 = super.getContext().pop();
		
		double res = this.calc(v2, v1);
		System.out.println("R: " + res); // OK to have side effects and dependency on System?
		getContext().push(res);
	}

	public abstract double calc(double v1, double v2);
}
