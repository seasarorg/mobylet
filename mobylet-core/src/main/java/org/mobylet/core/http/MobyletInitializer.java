/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.core.http;

import org.mobylet.core.detector.impl.MobyletCarrierDetector;
import org.mobylet.core.device.impl.MobyletDevicePool;
import org.mobylet.core.device.impl.ValueEngineDeviceReader;
import org.mobylet.core.dialect.impl.DefaultDialect;
import org.mobylet.core.dialect.impl.MobyletAuDialect;
import org.mobylet.core.dialect.impl.MobyletDocomoDialect;
import org.mobylet.core.dialect.impl.MobyletSoftbankDialect;
import org.mobylet.core.emoji.impl.MobyletEmojiPoolFamily;
import org.mobylet.core.emoji.impl.MobyletEmojiPoolReader;
import org.mobylet.core.selector.impl.MobyletDialectSelector;
import org.mobylet.core.util.SingletonUtils;

public class MobyletInitializer {

	public static void initialize() {
		SingletonUtils.put(new MobyletRequestHolder());
		SingletonUtils.put(new MobyletCarrierDetector());
		SingletonUtils.put(new MobyletDialectSelector());
		SingletonUtils.put(new MobyletDocomoDialect());
		SingletonUtils.put(new MobyletAuDialect());
		SingletonUtils.put(new MobyletSoftbankDialect());
		SingletonUtils.put(new DefaultDialect());
		SingletonUtils.put(new MobyletEmojiPoolFamily());
		SingletonUtils.put(new MobyletEmojiPoolReader());
		SingletonUtils.put(new ValueEngineDeviceReader());
		SingletonUtils.put(new MobyletDevicePool());
	}
}
