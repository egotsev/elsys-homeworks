package org.elsys.postfix;

import java.util.*;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
	}

	@Override
	public void eval() {
		Stack<Double> currentContext = getContext();
		if(currentContext.size()<2){
			throw new IllegalStateException();
		}else{
			double numberA = currentContext.pop();
			double numberB = currentContext.pop();
			double result = calc(numberA, numberB);
			System.out.println("R: " + result);
			getContext().push(result);
		}

	}

	public abstract double calc(double v1, double v2);
}
