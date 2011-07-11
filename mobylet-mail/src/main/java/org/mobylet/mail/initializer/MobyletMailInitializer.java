/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.mobylet.mail.initializer;

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

}
