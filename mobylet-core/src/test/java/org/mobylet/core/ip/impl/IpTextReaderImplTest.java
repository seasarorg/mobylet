package org.mobylet.core.ip.impl;

import junit.framework.TestCase;

import org.mobylet.core.Carrier;
import org.mobylet.core.ip.IpTextReader;
import org.mobylet.core.launcher.MobyletLauncher;
import org.mobylet.core.util.SingletonUtils;

public class IpTextReaderImplTest extends TestCase {

	public void test_reading() {
		//## ARRANGE ##
		MobyletLauncher.launch();

		//## ACT ##
		IpTextReader reader = SingletonUtils.get(IpTextReader.class);

		//## ASSERT ##
		assertTrue(reader.getIpAddressList(Carrier.DOCOMO).containsIp("210.153.084.001"));

	}
}
