package org.elsys.homework4;

public class Multiply extends BinaryOperator{

	public Multiply(){
		super("*");
	}
	
	public double calc(double v1, double v2){
		return v1 * v2;
	}
}
