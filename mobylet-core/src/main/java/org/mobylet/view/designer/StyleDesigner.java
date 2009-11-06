package org.mobylet.view.designer;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.StringUtils;


public class StyleDesigner extends SingletonDesigner {

	public static final String PREFIX_LINK_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"";

	public static final String SUFFIX_LINK_TAG = "\" />";


	public String importStyle(String src, boolean isLocalPath, boolean useCache) {
		if (StringUtils.isEmpty(src)) {
			return "";
		}
		Mobylet m = MobyletFactory.getInstance();
		if (m == null) {
			return "";
		}
		switch (m.getCarrier()) {
		case DOCOMO:
			break;
		default:
			return PREFIX_LINK_TAG + src + SUFFIX_LINK_TAG;
		}
		return "";
	}

}
