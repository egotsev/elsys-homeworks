package org.elsys.postfix;

public class Minus extends BinaryOperation
{
	
	public Minus()
	{
		super("-");
	}

	@Override
	public double calc(double numberOne, double numberTwo)
	{
		return numberTwo - numberOne;
	}

}