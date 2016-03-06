package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		// TODO Auto-generated method stub
		//double v1 = getContext().pop();
		double v2 = getContext().pop();
		
		
		if(v2 != getContext().pop()) {
			System.out.println("Error!");
		}
	}

	public abstract double calc(double v1, double v2);
}
