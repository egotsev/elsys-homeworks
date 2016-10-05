package org.elsys.postfix;

public class Divide extends BinaryOperation{
	
	public Divide() {
		super("/");
	}

	@Override
	public double calc(double v1, double v2) {
		if(v1 != 0)
		{
			double res = v2 / v1;
			System.out.println("R: " + res);
			getContext().push(res);
			return res;
		}
		throw new IllegalArgumentException("Argument divisor is 0");
	}

}
