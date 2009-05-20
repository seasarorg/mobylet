package org.mobylet.core.http;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.util.SingletonUtils;

public class MobyletFilterTest extends TestCase {

	public void test_init() throws ServletException {
		//## ARRANGE ##
		MobyletFilter filter = new MobyletFilter();

		//## ACT ##
		filter.init(null);

		//## ASSERT ##
		assertTrue(SingletonUtils.get(MobyletInitializer.class).isInitialized());
	}
}
