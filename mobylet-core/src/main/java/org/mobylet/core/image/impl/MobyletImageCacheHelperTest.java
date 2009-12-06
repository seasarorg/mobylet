package org.mobylet.core.image.impl;

import java.io.File;

import junit.framework.TestCase;


public class MobyletImageCacheHelperTest extends TestCase {

	public void test_getCacheKey() {
		String url = "http://localhost/mobylet/test.jsp";
		url = url.replace("://", File.separator)
		.replace(":", File.separator)
		.replace("/", File.separator)
		.replace("?", "%Q")
		.replace("&", "%P")
		.replace("=", "%L")
		.replace(";", "%S");
	}
}
