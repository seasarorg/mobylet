package org.mobylet.mail.detector.impl;

import org.mobylet.core.Carrier;
import org.mobylet.mail.detector.MailCarrierDetector;

import junit.framework.TestCase;


public class MobyletMailCarrierDetectorTest extends TestCase {

	public void test_getCarrierByAddress(){
		//## ARRANGE ##
		MailCarrierDetector carrierDetector = new MobyletMailCarrierDetector();

		//## ACT & ASSERT
		assertEquals(Carrier.DOCOMO, carrierDetector.getCarrierByAddress("mobylet@docomo.ne.jp"));
		assertEquals(Carrier.AU, carrierDetector.getCarrierByAddress("mobylet@ezweb.ne.jp"));
		assertEquals(Carrier.AU, carrierDetector.getCarrierByAddress("mobylet@mobylet.biz.ezweb.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@disney.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@softbank.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@d.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@h.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@t.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@k.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@r.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@s.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@n.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@k.vodafone.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-d.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-h.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-t.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-k.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-r.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-s.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-n.ne.jp"));
		assertEquals(Carrier.SOFTBANK, carrierDetector.getCarrierByAddress("mobylet@jp-k.ne.jp"));


		assertEquals(Carrier.OTHER, carrierDetector.getCarrierByAddress("mobylet@mobylet.org"));
	}

}
