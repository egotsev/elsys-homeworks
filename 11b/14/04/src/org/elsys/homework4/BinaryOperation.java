package org.elsys.homework4;

public abstract class BinaryOperation extends Operation {

	public BinaryOperation(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eval() {
		try
		{
			double v1 = context.pop();
			double v2 = context.pop();
			calc(v1, v2);
		}
		catch(IllegalStateException ex)
		{
			System.out.println("Not enoug arguments...");
		}

	}

	public abstract double calc(double v1, double v2);
}
