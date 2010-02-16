package org.mobylet.core.session.impl;

import junit.framework.TestCase;

import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.launcher.MobyletLauncher;
import org.mobylet.core.util.SingletonUtils;

public class MobyletSessionImplTest extends TestCase {

	public void test_session() {
		//## ARRANGE ##
		MobyletLauncher.launch();
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);

		//## ACT ##
		String obj = "ABC";
		holder.set("UID", obj);

		//## ASSERT ##
		assertEquals(holder.get("UID", obj.getClass()), obj);
	}
}
