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
