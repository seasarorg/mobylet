package org.mobylet.core.detector.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Carrier;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.util.MobyletRequestUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletCarrierDetector implements CarrierDetector {

	protected HashMap<Carrier, Pattern> patternMap;


	public MobyletCarrierDetector() {
		initialize();
	}

	@Override
	public Carrier getCarrier(HttpServletRequest request) {
		Object carrierObj = request.getAttribute(Carrier.class.getName());
		if (carrierObj == null || !(carrierObj instanceof Carrier)) {
			String userAgent = MobyletRequestUtils.getUserAgent(request);
			if (StringUtils.isNotEmpty(userAgent)) {
				Set<Entry<Carrier, Pattern>> entrySet = patternMap.entrySet();
				for (Entry<Carrier, Pattern> entry : entrySet) {
					if (entry.getValue().matcher(userAgent).matches()) {
						return entry.getKey();
					}
				}
			}
		} else {
			return Carrier.class.cast(carrierObj);
		}
		return Carrier.OTHER;
	}

	protected void initialize() {
		patternMap = new LinkedHashMap<Carrier, Pattern>();
		patternMap.put(Carrier.DOCOMO, Pattern.compile("^DoCoMo.+"));
		patternMap.put(Carrier.AU, Pattern.compile("^KDDI.+"));
		patternMap.put(Carrier.SOFTBANK, Pattern.compile("^(Vodafone|SoftBank|MOT).+"));
	}
}
