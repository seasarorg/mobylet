package org.mobylet.mail.initializer;

import java.util.Properties;

import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.builder.impl.MobyletDecoMailBuilder;
import org.mobylet.mail.builder.impl.MobyletHtmlMailBuilder;
import org.mobylet.mail.builder.impl.MobyletTextMailBuilder;
import org.mobylet.mail.config.impl.MobyletMailConfig;
import org.mobylet.mail.config.impl.MobyletMailMimeConfig;
import org.mobylet.mail.detector.impl.MobyletMailCarrierDetector;
import org.mobylet.mail.selector.impl.MobyletMailCharsetSelector;

public class MobyletMailInitializer implements MobyletInitializer {

	protected boolean isInitialize = false;

	@Override
	public void initialize() {
		//Config
		SingletonUtils.put(new MobyletMailConfig());
		SingletonUtils.put(new MobyletMailMimeConfig());
		//Detector
		SingletonUtils.put(new MobyletMailCarrierDetector());
		//Selector
		SingletonUtils.put(new MobyletMailCharsetSelector());
		//Builder
		SingletonUtils.put(new MobyletTextMailBuilder());
		SingletonUtils.put(new MobyletHtmlMailBuilder());
		SingletonUtils.put(new MobyletDecoMailBuilder());
		isInitialize = true;
	}

	@Override
	public boolean isInitialized() {
		return isInitialize;
	}

	@Override
	public void readProperties(Properties props) {
		//NOP
	}

}
