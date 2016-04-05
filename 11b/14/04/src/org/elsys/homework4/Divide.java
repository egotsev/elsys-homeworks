package org.elsys.homework4;

public class Divide extends BinaryOperation {

	public Divide()
	{
		super("/");
	}
	
	@Override
	public double calc(double v1, double v2) {
		double res = v1 / v2; 
		System.out.println("R: " + res); 
		getContext().push(res); 
		return 0;
	}

}
