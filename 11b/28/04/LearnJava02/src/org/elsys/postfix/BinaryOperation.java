package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {
	
	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() {
			
		double[] values = {0.0d, 0.0d};
		if (!(super.getContext().size() < 2)) {
			values[0] = super.getContext().pop();
			values[1] = super.getContext().pop();
		} else {
			throw new IllegalStateException();
		}
		
		double result = 0.0d;
		result = this.calc(values[1], values[0]);
		System.out.println("R: " + result);
		getContext().push(result);
	}

	public abstract double calc(double v1, double v2);
}
