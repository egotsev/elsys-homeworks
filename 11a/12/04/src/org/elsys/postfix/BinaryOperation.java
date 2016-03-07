package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		// TODO Auto-generated method stub
		if(getContext().size() < 2){
			throw new IllegalStateException();
		}else{
			double v1,v2;
			v2 = getContext().pop();
			v1 = getContext().pop();
			calc(v1, v2);
		}
	}

	public abstract double calc(double v1, double v2);
}
