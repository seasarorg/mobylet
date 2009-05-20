package org.seasar.mobylet.filter;

import javax.servlet.ServletException;

import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.http.MobyletFilter;
import org.mobylet.core.util.SingletonUtils;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.mobylet.http.S2MobyletFilter;

public class S2MobyletFilterTest extends S2TestCase {

	protected MobyletFilter filter;

	@Override
	protected void setUp() throws Exception {
		filter = new S2MobyletFilter();
		filter.init(null);
		include("app.dicon");
		super.setUp();
	}

	public void test_init() throws ServletException {
		//## ARRANGE ##

		//## ACT ##

		//## ASSERT ##
		assertTrue(SingletonUtils.get(EmojiPoolFamily.class) != null);
	}

}
