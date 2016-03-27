package org.elsys.postfix;

public abstract class BinaryOperation extends Operation 
{

	public BinaryOperation(String name) 
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() 
	{
		if(getContext().size() < 2)
		{
			throw new IllegalStateException();
		}
		else
		{
			double v2 = getContext().pop();
			double v1 = getContext().pop();
			calc(v1,v2);
		}
	}

	public abstract double calc(double v1, double v2);
}
