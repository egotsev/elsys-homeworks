package org.elsys.postfix;

import java.util.Stack;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		// TODO Auto-generated method stub
		
		Stack<Double> Context = getContext();
		
		if(Context.size() < 2) {
			throw new IllegalStateException();
		} else {
			double v1 = Context.pop();
			double v2 = Context.pop();
			double res = calc(v1, v2);
			System.out.println("R: " + res);
			getContext().push(res);
		}
	}

	public abstract double calc(double v1, double v2);
}
