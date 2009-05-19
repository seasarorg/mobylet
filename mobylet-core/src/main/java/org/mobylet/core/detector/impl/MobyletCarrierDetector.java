package org.mobylet.core.detector.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Carrier;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletCarrierDetector implements CarrierDetector {

	private static final String KEY = Carrier.class.getName();

	protected HashMap<Carrier, Pattern> patternMap;


	public MobyletCarrierDetector() {
		initialize();
	}

	@Override
	public Carrier getCarrier() {
		HttpServletRequest request = RequestUtils.get();
		Object carrierObj = request.getAttribute(KEY);
		if (carrierObj == null || !(carrierObj instanceof Carrier)) {
			String userAgent = RequestUtils.getUserAgent();
			if (StringUtils.isNotEmpty(userAgent)) {
				Set<Entry<Carrier, Pattern>> entrySet = patternMap.entrySet();
				for (Entry<Carrier, Pattern> entry : entrySet) {
					if (entry.getValue().matcher(userAgent).matches()) {
						request.setAttribute(KEY, entry.getKey());
						return getCarrier();
					}
				}
			}
		} else {
			return Carrier.class.cast(carrierObj);
		}
		request.setAttribute(KEY, Carrier.OTHER);
		return getCarrier();
	}

	protected void initialize() {
		patternMap = new LinkedHashMap<Carrier, Pattern>();
		patternMap.put(Carrier.DOCOMO, Pattern.compile("^DoCoMo.+"));
		patternMap.put(Carrier.AU, Pattern.compile("^KDDI.+"));
		patternMap.put(Carrier.SOFTBANK, Pattern.compile("^(Vodafone|SoftBank|MOT).+"));
	}
}
