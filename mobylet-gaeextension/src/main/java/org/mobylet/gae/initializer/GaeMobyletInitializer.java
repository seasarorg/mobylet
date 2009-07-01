package org.mobylet.gae.initializer;

import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.gae.image.GaeImageScaler;
import org.mobylet.gae.image.cache.GaeImageCacheHelper;

public class GaeMobyletInitializer implements MobyletInitializer {

	protected boolean isInitialize = false;

	@Override
	public void initialize() {
		SingletonUtils.put(new GaeImageScaler());
		SingletonUtils.put(new GaeImageCacheHelper());
		isInitialize = true;
	}

	@Override
	public boolean isInitialized() {
		return isInitialize;
	}

}
