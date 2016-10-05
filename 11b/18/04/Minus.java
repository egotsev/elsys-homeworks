package org.elsys.homework4;

public class Minus extends BinaryOperator{

	public Minus(){
		super("-");
	}
	
	
	public double calc(double v1, double v2){
		return v1 - v2;
	}
}
