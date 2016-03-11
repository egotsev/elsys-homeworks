package org.elsys.postfix.dev;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public abstract class PostfixTestCase {
	
	protected Postfix postfix;
	
	@Before
	public abstract void setUp() throws Exception;
	
	@After
	public abstract void tearDown() throws Exception;
	
	@Test
	public abstract void test() throws Exception;
}
