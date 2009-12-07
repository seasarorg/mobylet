package org.mobylet.view.designer;

import java.io.InputStream;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;
import org.mobylet.view.config.CSSConfig;
import org.mobylet.view.css.CSSCondContainer;
import org.mobylet.view.css.CSSParser;
import org.mobylet.view.css.XhtmlNode;


public class CSSDesigner extends SingletonDesigner {

	public static final String PREFIX_LINK_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"";

	public static final String SUFFIX_LINK_TAG = "\" />";

	public static final String PREFIX_STYLE_TAG = "<style type=\"text/css\"><![CDATA[";

	public static final String SUEFIX_STYLE_TAG = "]]></style>";


	public static final XhtmlNode NODE_A_LINK = new XhtmlNode("a:link", null, null, 2);

	public static final XhtmlNode NODE_A_VISITED = new XhtmlNode("a:visited", null, null, 2);

	public static final XhtmlNode NODE_A_ACTIVE = new XhtmlNode("a:active", null, null, 2);

	public static final XhtmlNode NODE_A_HOVER = new XhtmlNode("a:hover", null, null, 2);


	public static CSSConfig config = new CSSConfig();


	public String includeCSS(String src, String charset) {
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
			//Load-CSS
			InputStream is = null;
			try {
				is = ResourceUtils.getResourceFileOrInputStream(path);
				if (is != null) {
//					return PREFIX_STYLE_TAG
//							+ new String(InputStreamUtils.getAllBytes(is), Charset.forName(charset))
//							+ SUEFIX_STYLE_TAG;
					if (RequestUtils.get() != null &&
							RequestUtils.getMobyletContext() != null) {
						CSSParser parser = SingletonUtils.get(CSSParser.class);
						CSSCondContainer container = parser.parse(is);
						CSSCondContainer parentContainer =
							RequestUtils.getMobyletContext().get(CSSCondContainer.class);
						if (parentContainer != null) {
							parentContainer.putAll(container);
						} else {
							RequestUtils.getMobyletContext().set(container);
						}
						return getAnchorStyle(container);
					}
				}
			} finally {
				InputStreamUtils.closeQuietly(is);
			}
			break;
		default:
			return PREFIX_LINK_TAG + src + SUFFIX_LINK_TAG;
		}
		return "";
	}

	public String getAnchorStyle(CSSCondContainer container) {
		StringBuilder buf = new StringBuilder();
		String styleLink = container.getStyle(NODE_A_LINK);
		String styleVisited = container.getStyle(NODE_A_VISITED);
		String styleActive = container.getStyle(NODE_A_ACTIVE);
		String styleHover = container.getStyle(NODE_A_HOVER);
		if (StringUtils.isNotEmpty(styleLink)) {
			buf.append(NODE_A_LINK.getTag() + "{" + styleLink + "}");
		}
		if (StringUtils.isNotEmpty(styleVisited)) {
			buf.append(NODE_A_VISITED.getTag() + "{" + styleVisited + "}");
		}
		if (StringUtils.isNotEmpty(styleActive)) {
			buf.append(NODE_A_ACTIVE.getTag() + "{" + styleActive + "}");
		}
		if (StringUtils.isNotEmpty(styleHover)) {
			buf.append(NODE_A_HOVER.getTag() + "{" + styleHover + "}");
		}
		return buf.toString();
	}
}
