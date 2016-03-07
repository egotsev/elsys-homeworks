package org.elsys.homework4;



public abstract class BinaryOperator extends Operation {

	public BinaryOperator(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		// TODO Auto-generated method stub
		if (getContext().size() < 2) {
			throw new IllegalStateException();
		}else{
			calc(getContext().firstElement(), getContext().lastElement());
		}
	}

	public abstract double calc(double v1, double v2);
}
