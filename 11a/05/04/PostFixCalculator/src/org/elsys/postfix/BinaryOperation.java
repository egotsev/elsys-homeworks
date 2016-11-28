package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() {
			if(getContext().size() < 2) {
				throw new IllegalStateException("Binary operation cannot be performed on 1 argument");
			}
			else {
				double v1 = getContext().pop();
				double v2 = getContext().pop();
				double result = calc(v1,v2);
				getContext().push(result);
				System.out.println(result);
			}
	}

	public abstract double calc(double v1, double v2);
}
