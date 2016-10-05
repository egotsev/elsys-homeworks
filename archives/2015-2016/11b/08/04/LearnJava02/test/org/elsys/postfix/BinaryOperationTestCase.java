package org.elsys.postfix;

import static org.junit.Assert.*;
import java.util.Scanner;
import java.util.Stack;
import org.junit.Test;

public class BinaryOperationTestCase {

	@Test(expected = java.util.EmptyStackException.class)
	public void test() {
		Plus p = new Plus();
		p.setContext(new Stack<Double>());
		p.getContext().push(8.0);
		p.eval();
	}

}
