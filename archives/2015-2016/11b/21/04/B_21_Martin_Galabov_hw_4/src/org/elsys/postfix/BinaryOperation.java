package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() {
		if(getContext().size() >= 2){
			calc(getContext().pop(),getContext().pop());
		} else {
			throw new IllegalStateException("Not enough arguments...");
		}
	}

	public abstract void calc(double v1, double v2);
}
