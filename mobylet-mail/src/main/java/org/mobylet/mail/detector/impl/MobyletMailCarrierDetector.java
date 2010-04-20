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
package org.mobylet.mail.detector.impl;

import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.util.StringUtils;
import org.mobylet.mail.detector.MailCarrierDetector;

public class MobyletMailCarrierDetector implements MailCarrierDetector {

	public static final Pattern PTN_DOCOMO =
		Pattern.compile("^.+@docomo[.]ne[.]jp$");

	public static final Pattern PTN_AU =
		Pattern.compile("^.+@(ezweb|.+[.]biz[.]ezweb)[.]ne[.]jp$");

	public static final Pattern PTN_SOFTBANK =
		Pattern.compile("^.+@(disney|softbank|" +
				"[dhtkrsnq][.]vodafone|jp-[dhtkrsnq])[.]ne[.]jp$");


	@Override
	public Carrier getCarrierByAddress(String address) {
		if (StringUtils.isEmpty(address)) {
			return null;
		}
		if (PTN_DOCOMO.matcher(address).matches()) {
			return Carrier.DOCOMO;
		} else if (PTN_AU.matcher(address).matches()) {
			return Carrier.AU;
		} else if (PTN_SOFTBANK.matcher(address).matches()) {
			return Carrier.SOFTBANK;
		} else {
			return Carrier.OTHER;
		}
	}

}
