package org.elsys.postfix;

public class Divide extends BinaryOperation
{
	
	public Divide()
	{
		super("/");
	}

	@Override
	public double calc(double numberOne, double numberTwo)
	{
		return numberTwo / numberOne;
	}

}