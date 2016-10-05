package org.elsys.postfix;

import java.util.*;

public abstract class BinaryOperation extends Operation
{

	public BinaryOperation(String name)
	{
		super(name);
	}

	@Override
	public void eval()
	{
		Stack<Double> contextNow = getContext();
		if(!(contextNow.size()<2))
		{
			double numberOne = contextNow.pop();
			double numberTwo = contextNow.pop();
			double result = calc(numberOne, numberTwo);
			System.out.println("R: " + result);
			getContext().push(result);
		}
		else
		{	
			throw new IllegalStateException();
		}

	}

	public abstract double calc(double v1, double v2);
}