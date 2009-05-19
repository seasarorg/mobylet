package org.mobylet.core.selector.impl;

import org.mobylet.core.Carrier;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.dialect.impl.DefaultDialect;
import org.mobylet.core.dialect.impl.MobyletAuDialect;
import org.mobylet.core.dialect.impl.MobyletDocomoDialect;
import org.mobylet.core.dialect.impl.MobyletSoftbankDialect;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.util.SingletonUtils;

public class MobyletDialectSelector implements DialectSelector {


	public MobyletDialect getDialect(Carrier carrier) {
		switch (carrier) {
		case DOCOMO:
			return SingletonUtils.get(MobyletDocomoDialect.class);
		case AU:
			return SingletonUtils.get(MobyletAuDialect.class);
		case SOFTBANK:
			return SingletonUtils.get(MobyletSoftbankDialect.class);
		default:
			return SingletonUtils.get(DefaultDialect.class);
		}
	}

}
