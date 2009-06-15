package org.mobylet.gae.initializer.impl;

import org.mobylet.core.initializer.impl.MobyletInitializerImpl;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.gae.image.GaeImageScaleHelper;

public class GaeMobyletInitializer extends MobyletInitializerImpl {

	@Override
	public void initialize() {
		super.initialize();
		SingletonUtils.put(new GaeImageScaleHelper());
	}

}
