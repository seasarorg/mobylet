/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
