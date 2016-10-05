package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		// TODO Auto-generated method stub
		if(this.getContext().size() < 2){
			throw new IllegalStateException();
		}
		double v1 = this.getContext().pop();
		double v2 = this.getContext().pop();
		
		double res = calc(v2,v1);
		this.getContext().push(res);
		

	}

	public abstract double calc(double v1, double v2);
}
