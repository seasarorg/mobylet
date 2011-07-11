/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.mobylet.core.holder.impl;

import junit.framework.TestCase;

import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.launcher.MobyletLauncher;
import org.mobylet.core.util.SingletonUtils;

public class MobyletSessionHolderTest extends TestCase {

	public void test_sessionInvoke() {
		//## ARRANGE ##
		MobyletLauncher.launch();
		MobyletSessionConfig config = SingletonUtils.get(MobyletSessionConfig.class);
		MobyletSessionHolder holder = new MobyletSessionHolder();
		holder.timeSpan = 10;

		String key = "key";
		String val = "ABC";
		holder.set(key, val);

		assertTrue(val.equals(holder.get(key, String.class)));

		holder.remove(key, String.class);

		assertTrue(holder.get(key, String.class) == null);

		holder.set(key, val);
		assertTrue(val.equals(holder.get(key, String.class)));
		try {
			Thread.sleep(config.getTimeout() * holder.timeSpan + 100);
		} catch (InterruptedException e) {
			//NOP
		}
		assertTrue(holder.get(key, String.class) == null);
	}

}
