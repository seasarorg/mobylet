package org.mobylet.core.http;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class MobyletResponseTest extends TestCase {

	public void test_Regex() {
		Pattern REG_JSESSIONID = Pattern.compile(";jsessionid=[^?]+");	
		String url = "http://localhost:8080/koicho/;jsessionid=EE54541A49857E11D113F1139B88F61F";
		
		assertEquals(REG_JSESSIONID.matcher(url).replaceFirst(""), "http://localhost:8080/koicho/");
	}
}
