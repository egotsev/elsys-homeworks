package org.elsys.postfix;

public class Divide extends BinaryOperation{
	public Divide() {
		super("/");
	}

	@Override
	public double calc(double v1,double v2) {
		if(v1 == 0){
			throw new IllegalArgumentException();
		}
		double res = v2 / v1;
		System.out.println("R: " + res);
		if(BinaryTest.status == false){
			getContext().push(res);
		}
		return res;
	}
}
