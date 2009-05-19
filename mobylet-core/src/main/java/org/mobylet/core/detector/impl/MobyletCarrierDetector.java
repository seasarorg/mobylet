package org.mobylet.core.detector.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletCarrierDetector implements CarrierDetector {


	protected HashMap<Carrier, Pattern> patternMap;


	public MobyletCarrierDetector() {
		initialize();
	}

	@Override
	public Carrier getCarrier() {
		MobyletContext context = RequestUtils.getMobyletContext();
		Carrier carrier = context.get(Carrier.class);
		if (carrier == null) {
			String userAgent = RequestUtils.getUserAgent();
			if (StringUtils.isNotEmpty(userAgent)) {
				Set<Entry<Carrier, Pattern>> entrySet = patternMap.entrySet();
				for (Entry<Carrier, Pattern> entry : entrySet) {
					if (entry.getValue().matcher(userAgent).matches()) {
						context.set(entry.getKey());
						return getCarrier();
					}
				}
			}
		} else {
			return carrier;
		}
		context.set(Carrier.OTHER);
		return getCarrier();
	}

	protected void initialize() {
		patternMap = new LinkedHashMap<Carrier, Pattern>();
		patternMap.put(Carrier.DOCOMO, Pattern.compile("^DoCoMo.+"));
		patternMap.put(Carrier.AU, Pattern.compile("^KDDI.+"));
		patternMap.put(Carrier.SOFTBANK, Pattern.compile("^(Vodafone|SoftBank|MOT).+"));
	}
}
