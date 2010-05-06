/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.view.designer;

import java.io.InputStream;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.config.MobyletConfig;
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
import org.mobylet.view.xhtml.XhtmlNode;


public class CSSDesigner extends SingletonDesigner {

	public static final String PREFIX_LINK_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"";

	public static final String SUFFIX_LINK_TAG = "\" />";

	public static final String PREFIX_STYLE_TAG = "<style type=\"text/css\">";

	public static final String SUEFIX_STYLE_TAG = "</style>";


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
			MobyletConfig mobyletConfig = SingletonUtils.get(MobyletConfig.class);
			if (!mobyletConfig.useCSSExpand()) {
				return PREFIX_LINK_TAG + src + SUFFIX_LINK_TAG;
			}
			String path = null;
			if (PathUtils.isNetworkPath(src)) {
				path = src;
			} else {
				if (StringUtils.isNotEmpty(config.getLocalBasePath())) {
					path = config.getLocalBasePath()
							+ (src.startsWith("/") ? "" : "/")
							+ src;
				} else {
					String url = UrlUtils.getCurrentUrl();
					if (src.startsWith("/")) {
						int protocolIndex = url.indexOf("://");
						if (protocolIndex >= 0) {
							int index = url.indexOf("/", protocolIndex+3);
							if (index > 0) {
								url = url.substring(0, index);
							}
						}
						path = url + src;
					} else {
						int last = url.lastIndexOf('/');
						if (last > 0) {
							url = url.substring(0, last+1);
						} else if (!url.endsWith("/")) {
							url = url + "/";
						}
						path = url + src;
					}
				}
			}
			//Load-CSS
			InputStream is = null;
			try {
				is = ResourceUtils.getResourceFileOrInputStream(path);
				if (is != null) {
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
		if (buf.length() > 0) {
			return PREFIX_STYLE_TAG
				+ buf.toString()
				+ SUEFIX_STYLE_TAG;
		}
		return "";
	}
}
