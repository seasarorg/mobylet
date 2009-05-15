package org.mobylet.core.selector.impl;

import org.mobylet.core.Carrier;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.dialect.impl.DefaultDialect;
import org.mobylet.core.selector.DialectSelector;

public class MobyletDialectSelector implements DialectSelector {

	protected final MobyletDialect DEFAULT_DIALECT = new DefaultDialect();


	public MobyletDialect getDialect(Carrier carrier) {
		return null;
	}

	public MobyletDialect getDefaultDialect() {
		return DEFAULT_DIALECT;
	}
}
