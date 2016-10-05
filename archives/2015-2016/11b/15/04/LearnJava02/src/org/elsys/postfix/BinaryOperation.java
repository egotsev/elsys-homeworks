package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void eval() {
		if(getContext().size() < 2)
		{
			throw new IllegalStateException("You have less than 2 arguments!");
		}
		calc(getContext().pop(),getContext().pop());
	}

	public abstract double calc(double v1, double v2);
	
}
