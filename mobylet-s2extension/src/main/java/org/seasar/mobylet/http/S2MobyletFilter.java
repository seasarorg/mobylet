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
package org.seasar.mobylet.http;

import javax.servlet.FilterConfig;

import org.mobylet.core.http.MobyletFilter;
import org.mobylet.core.util.SingletonUtils;
import org.seasar.mobylet.holder.S2MobyletSingletonHolder;

public class S2MobyletFilter extends MobyletFilter {

	@Override
	protected void initSingletonContainer(FilterConfig filterConfig) {
		if (!SingletonUtils.isInitialized()) {
			SingletonUtils.initialize(S2MobyletSingletonHolder.class);
		}
		super.initSingletonContainer(filterConfig);
	}

	/*
	@Override
	protected void initInitializer(FilterConfig filterConfig) {
		//NOP
	}
	*/

}
