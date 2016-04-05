package org.elsys.homework4;

public class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}
	
	@Override
	public double calc(double v1, double v2) {
		double res = v1 + v2; 
		System.out.println("R: " + res); 
		getContext().push(res); 
		return 0;
	}

}
