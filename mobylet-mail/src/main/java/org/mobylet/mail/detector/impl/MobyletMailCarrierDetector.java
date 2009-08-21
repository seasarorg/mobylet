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
