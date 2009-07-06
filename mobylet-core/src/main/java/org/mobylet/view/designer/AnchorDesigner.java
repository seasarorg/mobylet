package org.mobylet.view.designer;

import org.mobylet.core.util.SingletonUtils;
import org.mobylet.view.config.TransitionConfig;

public class AnchorDesigner extends TransitionDesigner {


	public static AnchorDesigner getDesigner() {
		AnchorDesigner designer = null;
		try {
			designer = SingletonUtils.get(AnchorDesigner.class);
		} catch (Exception e) {
			//NOP
		}
		if (designer == null) {
			SingletonUtils.put(new AnchorDesigner());
			return getDesigner();
		}
		return designer;
	}

	public String getHref(String url) {
		return getHref(url, config);
	}

	public String getHref(String url, TransitionConfig config) {
		return constructUrl(url, config);
	}

}
