package org.elsys.homework4;

public class Devide extends BinaryOperator{
	public Devide(){
		super("/");
	}
	
	public double calc(double v1, double v2){
		return v1 / v2;
	}
}
