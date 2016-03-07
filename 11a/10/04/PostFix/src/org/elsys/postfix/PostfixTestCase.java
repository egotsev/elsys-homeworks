package org.elsys.postfix;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class PostfixTestCase {

	protected Postfix postfix;
	
	@Before
	public abstract void setUp() throws Exception;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public abstract void test();

}