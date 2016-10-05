package org.elsys.postfix;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() {
		Stack<Double> context = getContext();
 		if(context.size() < 2){
 			throw new IllegalStateException();
 		}
		double v1 = getContext().pop();
 		double v2 = getContext().pop();
		double res = calc(v1, v2);
		System.out.println("Result = " + res);
 		getContext().push(res);
 		}

	}

	public abstract double calc(double v1, double v2);
}
