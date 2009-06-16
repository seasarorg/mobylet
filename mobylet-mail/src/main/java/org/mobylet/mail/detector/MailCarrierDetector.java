package org.mobylet.mail.detector;

import org.mobylet.core.Carrier;

public interface MailCarrierDetector {

	public Carrier getCarrierByAddress(String address);

}
