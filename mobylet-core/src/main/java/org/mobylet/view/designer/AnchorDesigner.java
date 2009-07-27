package org.mobylet.view.designer;

import org.mobylet.view.config.TransitionConfig;

public class AnchorDesigner extends TransitionDesigner {


	public String getHref(String url) {
		return getHref(url, config);
	}

	public String getHref(String url, TransitionConfig config) {
		return constructUrl(url, config);
	}

}
