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
package org.mobylet.core.detector.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Carrier;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletCarrierDetector implements CarrierDetector {


	protected Set<MobyletDialect> dialects;


	public MobyletCarrierDetector() {
		initialize();
	}

	@Override
	public Carrier getCarrier() {
		MobyletContext context = RequestUtils.getMobyletContext();
		Carrier carrier = context.get(Carrier.class);
		if (carrier == null) {
			context.set(getCarrier(RequestUtils.get()));
			return getCarrier();
		} else {
			return carrier;
		}
	}

	@Override
	public Carrier getCarrier(HttpServletRequest request) {
		String userAgent = RequestUtils.getUserAgent(request);
		if (StringUtils.isNotEmpty(userAgent)) {
			for (MobyletDialect dialect : dialects) {
				if (dialect.getCarrierMatchRegex().matcher(userAgent).matches()) {
					return dialect.getCarrier();
				}
			}
		}
		return Carrier.OTHER;
	}

	protected void initialize() {
		dialects = new LinkedHashSet<MobyletDialect>();
		DialectSelector dialectSelector =
			SingletonUtils.get(DialectSelector.class);
		dialects.add(dialectSelector.getDialect(Carrier.DOCOMO));
		dialects.add(dialectSelector.getDialect(Carrier.AU));
		dialects.add(dialectSelector.getDialect(Carrier.SOFTBANK));
	}
}
