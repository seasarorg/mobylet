package org.mobylet.view.designer;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;
import org.mobylet.view.config.CSSConfig;


public class CSSDesigner extends SingletonDesigner {

	public static final String PREFIX_LINK_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"";

	public static final String SUFFIX_LINK_TAG = "\" />";

	public static final String PREFIX_STYLE_TAG = "<style type=\"text/css\"><![CDATA[";

	public static final String SUEFIX_STYLE_TAG = "]]></style>";


	public static CSSConfig config = new CSSConfig();


	public String includeCSS(String src, Charset charset) {
		if (StringUtils.isEmpty(src)) {
			return "";
		}
		Mobylet m = MobyletFactory.getInstance();
		if (m == null) {
			return "";
		}
		switch (m.getCarrier()) {
		case DOCOMO:
			String path = null;
			if (PathUtils.isNetworkPath(src)) {
				path = src;
			} else {
				if (StringUtils.isNotEmpty(config.getLocalBasePath())) {
					path = config.getLocalBasePath()
							+ (src.startsWith("/") ? "" : "/")
							+ src;
				} else {
					path = UrlUtils.getCurrentUrl()
							+ (src.startsWith("/") ? "" : "/")
							+ src;
				}
			}
			InputStream is = ResourceUtils.getResourceFileOrInputStream(path);
			if (is != null) {
				return PREFIX_STYLE_TAG
						+ new String(InputStreamUtils.getAllBytes(is), charset)
						+ SUEFIX_STYLE_TAG;
			}
			break;
		default:
			return PREFIX_LINK_TAG + src + SUFFIX_LINK_TAG;
		}
		return "";
	}

}
