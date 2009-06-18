package org.mobylet.mail.initializer;

import java.util.Properties;

import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.config.impl.MobyletMailConfig;
import org.mobylet.mail.detector.impl.MobyletMailCarrierDetector;
import org.mobylet.mail.parts.builder.HtmlPartsBuilder;
import org.mobylet.mail.parts.builder.TextPartsBuilder;
import org.mobylet.mail.selector.impl.MobyletMailCharsetSelector;

public class MobyletMailInitializer implements MobyletInitializer {

	protected boolean isInitialize = false;

	@Override
	public void initialize() {
		//Config
		SingletonUtils.put(new MobyletMailConfig());
		//Detector
		SingletonUtils.put(new MobyletMailCarrierDetector());
		//Selector
		SingletonUtils.put(new MobyletMailCharsetSelector());
		//Builder
		SingletonUtils.put(new TextPartsBuilder());
		SingletonUtils.put(new HtmlPartsBuilder());
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
