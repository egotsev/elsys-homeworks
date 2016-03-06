package org.elsys.postfix.dev;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
	}
	
	public void eval() {
		if(getContext().size() < 2) {
			System.out.println("Trying to perform a binary operation on a sole argument!");
			throw new IllegalStateException();
		}
		else {
			double v2 = getContext().pop();
			double v1 = getContext().pop();
			getContext().push(calc(v1, v2));
		}
	}
	
	public abstract double calc(double v1, double v2);

}
