package org.mobylet.core.selector;

import org.mobylet.core.Carrier;
import org.mobylet.core.dialect.MobyletDialect;

public interface DialectSelector {

	public MobyletDialect getDialect(Carrier carrier);

	public MobyletDialect getDefaultDialect();

}
