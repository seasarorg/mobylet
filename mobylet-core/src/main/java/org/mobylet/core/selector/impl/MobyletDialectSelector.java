/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
