package org.elsys.postfix;

public class Plus extends BinaryOperation{
	
	public Plus() {
		super("+");
	}

	@Override
	public double calc(double v1,double v2) {
		double res = v1 + v2;
		System.out.println("R: " + res);
		if(BinaryTest.status == false){
			getContext().push(res);
		}
		return res;
	}

}
