package org.elsys.postfix;

import java.security.InvalidParameterException;

public class Divide extends BinaryOperation {
	public Divide() {
		super("/");
	}
	
	@Override
	public double calc(double firstParam, double secondParam) {
		if (secondParam == 0) {
			throw new InvalidParameterException();
		}
		
		return firstParam / secondParam;
	}
}