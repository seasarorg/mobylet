package org.mobylet.core.initializer;

import java.util.Properties;

public interface MobyletInitializer {

	public void initialize();

	public boolean isInitialized();

	public void readProperties(Properties props);

}
