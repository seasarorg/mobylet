package org.mobylet.core.detector;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Carrier;

public interface CarrierDetector {

	public Carrier getCarrier(HttpServletRequest request);

}
