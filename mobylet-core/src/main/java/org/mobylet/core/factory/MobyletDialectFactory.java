package org.mobylet.core.factory;

import org.mobylet.core.Carrier;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.dialect.impl.DefaultDialect;

public class MobyletDialectFactory {

	private static final MobyletDialect DEFAULT_DIALECT = new DefaultDialect();


	public static MobyletDialect getDialect(Carrier carrier) {
		return null;
	}

	public static MobyletDialect getDefaultDialect() {
		return DEFAULT_DIALECT;
	}
}
