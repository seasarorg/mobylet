package org.mobylet.core.http;

import org.mobylet.core.detector.impl.MobyletCarrierDetector;
import org.mobylet.core.device.impl.MobyletDevicePool;
import org.mobylet.core.device.impl.ValueEngineDeviceReader;
import org.mobylet.core.dialect.impl.DefaultDialect;
import org.mobylet.core.dialect.impl.MobyletAuDialect;
import org.mobylet.core.dialect.impl.MobyletDocomoDialect;
import org.mobylet.core.dialect.impl.MobyletSoftbankDialect;
import org.mobylet.core.emoji.impl.MobyletEmojiStockerFamily;
import org.mobylet.core.emoji.impl.MobyletEmojiStockerReader;
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
		SingletonUtils.put(new MobyletEmojiStockerFamily());
		SingletonUtils.put(new MobyletEmojiStockerReader());
		SingletonUtils.put(new ValueEngineDeviceReader());
		SingletonUtils.put(new MobyletDevicePool());
	}
}
