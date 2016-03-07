package org.elsys.postfix;

public class Divide extends BinaryOperation{
	
	public Divide() {
		super("/");
	}

	public double calc(double v1, double v2) {
		if(v2 == 0)
		{
			throw new IllegalArgumentException();
		}
		double res = v1 / v2;
		System.out.println("R: " + res);
		getContext().push(res);
		return res;
		}

}
