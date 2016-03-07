package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		// TODO Auto-generated method stub
		double v1;
		double v2;
		if(!getContext().empty()){
			v1 = getContext().pop();
			if(!getContext().empty()){
				v2 = getContext().pop();
				double res = calc(v1, v2);
				System.out.println("R: " + res);
				getContext().push(res);
			}else{
				throw new IllegalStateException("Not enough arguements");
			}
		}else{
			throw new IllegalStateException("Not enough arguements");
		}
	}

	public abstract double calc(double v1, double v2);
}
