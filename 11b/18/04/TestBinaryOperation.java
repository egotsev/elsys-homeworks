package org.elsys.homework4;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class TestBinaryOperation {

	
	private BinaryOperator bop;
	
	@Before
	public void setup() throws Exception{
		bop = new Plus();
	}
	
	
	 @Test(expected = IllegalStateException.class)
	     public void OneArgumentEval() throws Exception {
	         Stack<Double> postfixcalc = new Stack<>();
	         postfixcalc.add(2.0);
	         bop.setContext(postfixcalc);
	         bop.eval();
	     }
	
	 @Test(expected = IllegalStateException.class)
     public void CorrectEval() throws Exception {
         Stack<Double> postfixcalc = new Stack<>();
         postfixcalc.add(2.0);
         postfixcalc.add(4.0);
         bop.setContext(postfixcalc);
         bop.eval();
         
         assertEquals(6.0, bop.getContext().peek(), 0.000001);
	 }         
}
