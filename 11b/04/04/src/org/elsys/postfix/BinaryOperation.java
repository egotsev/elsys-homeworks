package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		Double v1 = super.getContext().pop();
		Double v2 = super.getContext().pop();
		if(v1 == null || v2 == null){
			throw new IllegalStateException();
		}
	    calc(v2,v1);
		// TODO Auto-generated method stub

	}

	public abstract double calc(double v1, double v2);
}
