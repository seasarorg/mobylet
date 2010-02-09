package org.mobylet.core.launcher;

import java.util.List;

import junit.framework.TestCase;

import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.util.SingletonUtils;


public class MobyletLauncherTest extends TestCase {

	public void test_launch() {
		MobyletLauncher.launch();
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		List<MobyletInitializer> initializers = config.getInitializers();
		assertEquals(initializers.size(), 1);
	}

}
