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
package org.mobylet.core.session.impl;

import junit.framework.TestCase;

import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.enums.SessionKey;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.ip.IpAddress;
import org.mobylet.core.launcher.LaunchConfig;
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

	public void test_session2() {
		//## ARRANGE ##
		LaunchConfig config = new LaunchConfig();
		config.addParameter("mobylet.config.dir", "test/");
		MobyletLauncher.initSingletonContainer();
		MobyletLauncher.initLogger(config);
		MobyletLauncher.initDefaultCharset(config);
		MobyletLauncher.initInitializer(config);
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);

		//## ACT ##
		String obj = "ABC";
		holder.set("UID", obj);

		//## ASSERT ##
		assertEquals(holder.get("UID", obj.getClass()), obj);
	}

	public void test_session2A() {
		//## ARRANGE ##
		LaunchConfig config = new LaunchConfig();
		config.addParameter("mobylet.config.dir", "sessionTest/");
		MobyletLauncher.initSingletonContainer();
		MobyletLauncher.initLogger(config);
		MobyletLauncher.initDefaultCharset(config);
		MobyletLauncher.initInitializer(config);
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);

		//## ACT ##
		String obj = "ABC";
		holder.set("UID", obj);

		//## ASSERT ##
		assertEquals(holder.get("UID", obj.getClass()), obj);
	}

	public void test_sessionConfig() {
		//## ARRANGE ##
		MobyletLauncher.launch();
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
