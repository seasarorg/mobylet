package org.mobylet.view.designer;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.UrlUtils;

public class GpsDesigner extends TransitionDesigner {


	public static GpsDesigner getDesigner() {
		GpsDesigner designer = null;
		try {
			designer = SingletonUtils.get(GpsDesigner.class);
		} catch (Exception e) {
			//NOP
		}
		if (designer == null) {
			SingletonUtils.put(new GpsDesigner());
			return getDesigner();
		}
		return designer;
	}


	protected String getGpsUrl(String url) {
		url = constructUrl(url);
		switch (MobyletFactory.getInstance().getCarrier()) {
		case DOCOMO:
			return constructUrl(url);
		case AU:
			url = url + UrlUtils.AMP + "ver=1&datum=0&unit=0&acry=0&number=0";
			return "device:gpsone?url=" + url.replace('?', '&');
		case SOFTBANK:
			return "location:gps?url=" + url.replace('?', '&');
		}
		return "";
	}

	protected String getEasyGpsUrl(String url) {
		url = constructUrl(url);
		switch (MobyletFactory.getInstance().getCarrier()) {
		case DOCOMO:
			return "http://w1m.docomo.ne.jp/cp/iarea?" +
				"ecode=OPENAREACODE&msn=OPENAREAKEY&posinfo=1" +
				"&nl=" + url.replace('?', '&').replaceAll("[=]", "%3d");
		case AU:
			return "device:location?url=" + url.replace('?', '&');
		case SOFTBANK:
			return "location:auto?url=" + url.replace('?', '&');
		}
		return "";
	}
}
