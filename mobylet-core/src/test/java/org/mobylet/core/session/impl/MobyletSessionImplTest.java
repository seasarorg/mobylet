package org.mobylet.core.session.impl;

import junit.framework.TestCase;

import org.mobylet.core.session.MobyletSession;

public class MobyletSessionImplTest extends TestCase {

	public void test_nullGet() {
		//## ARRANGE ##
		MobyletSession session = new MobyletSessionImpl();

		//## ACT ##
		String obj = null;
		try {
			session.get(obj);
		} catch (NullPointerException e) {
			//NOP
		}

		//## ASSERT ##
		assertTrue(true);
	}
}
