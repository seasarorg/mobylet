package org.mobylet.core.session.impl;

import junit.framework.TestCase;

import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.MobyletSessionConfig.Ip;
import org.mobylet.core.config.enums.SessionKey;
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

	public void test_sessionConfig() {
		//## ARRANGE ##
		MobyletSessionConfig config = new MobyletSessionConfig();

		//## ASSERT ##
		assertEquals(SessionKey.GUID, config.getKey());
		assertEquals("http", config.getDistribution().getProtocol());
	}

	public void test_ipIsAllow() {
		//## ARRANGE ##
		Ip ip = new Ip("192.168.0.0/24");

		//## ASSERT ##
		assertTrue(ip.isAllow("192.168.0.0"));
		assertTrue(ip.isAllow("192.168.0.10"));
		assertTrue(ip.isAllow("192.168.0.128"));
		assertTrue(ip.isAllow("192.168.0.255"));
		assertTrue(!ip.isAllow("192.168.1.0"));
		assertTrue(!ip.isAllow("193.168.0.0"));
	}

	public void test_ipIsAllow2() {
		//## ARRANGE ##
		Ip ip = new Ip("127.0.0.1");

		//## ASSERT ##
		assertTrue(ip.isAllow("127.0.0.1"));
	}

}
