package org.mobylet.gae.initializer;

import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.gae.image.GaeImageScaler;

public class GaeMobyletInitializer implements MobyletInitializer {

	protected boolean isInitialize = false;

	@Override
	public void initialize() {
		SingletonUtils.put(new GaeImageScaler());
		isInitialize = true;
	}

	@Override
	public boolean isInitialized() {
		return isInitialize;
	}

}
