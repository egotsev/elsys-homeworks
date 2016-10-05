package org.elsys.homework4;


public class Plus extends BinaryOperator{
	
	public Plus() {
		super("+");
	}

	@Override
	public double calc(double v1, double v2){
		return v1 + v2;
	}
	
}
