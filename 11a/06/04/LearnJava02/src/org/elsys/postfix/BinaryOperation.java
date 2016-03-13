package org.elsys.postfix;

import java.util.Stack;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) 
	{
		super(name);
	}

	@Override
	public void eval() 
	{
		Stack<Double> Stack = getContext();
		
		if (Stack.size() < 2)
		{
			throw new IllegalStateException();
		}
		else
		{
			double value1 = Stack.pop();
			double value2 = Stack.pop();
			
			double result = calc(value1, value2);
			
			System.out.println("Result: " + result);
			
			getContext().push(result);
		}
	}

	public abstract double calc(double v1, double v2);
}
