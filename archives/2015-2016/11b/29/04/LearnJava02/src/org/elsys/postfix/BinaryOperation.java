package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		if(this.getContext().size() >= 2){
			double res =  calc(this.getContext().pop(), this.getContext().pop());
			this.getContext().push(res);
		} else {
			throw new IllegalStateException();
		}

	}

	public abstract double calc(double v1, double v2);
}
