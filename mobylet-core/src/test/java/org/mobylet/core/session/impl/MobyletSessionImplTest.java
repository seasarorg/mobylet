package org.mobylet.core.session.impl;

import junit.framework.TestCase;

import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.enums.SessionKey;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.ip.IpAddress;
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
		IpAddress ip = new IpAddress("192.168.0.0/24");

		//## ASSERT ##
		assertTrue(ip.containsIp("192.168.0.0"));
		assertTrue(ip.containsIp("192.168.0.10"));
		assertTrue(ip.containsIp("192.168.0.128"));
		assertTrue(ip.containsIp("192.168.0.255"));
		assertTrue(!ip.containsIp("192.168.1.0"));
		assertTrue(!ip.containsIp("193.168.0.0"));
	}

	public void test_ipIsAllow2() {
		//## ARRANGE ##
		IpAddress ip = new IpAddress("127.0.0.1");

		//## ASSERT ##
		assertTrue(ip.containsIp("127.0.0.1"));
	}

}
