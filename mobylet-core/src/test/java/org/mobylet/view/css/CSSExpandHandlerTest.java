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
package org.mobylet.view.css;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.mobylet.view.xhtml.XhtmlParser;


public class CSSExpandHandlerTest extends TestCase {

	public void test_simple01() {
		String xml = "<test>AAA<a>BBB</a>CCC</test>";
		CSSExpandHandler handler = new CSSExpandHandler(null);
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue(xml.equals(handler.toString()));
	}

	public void test_simple02() {
		String xml = "<test>AAA<!--ABC--><a <!--ADB -->id=\"ID\" href=\"http://hoge.com/?a=1&b=c\">BBB</a>CCC</test>";
		String xmlPass = "<test>AAA<a id=\"ID\" href=\"http://hoge.com/?a=1&b=c\">BBB</a>CCC</test>";
		CSSExpandHandler handler = new CSSExpandHandler(null);
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue(xmlPass.equals(handler.toString()));
	}

	public void test_simple03() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC</test>";
		String css = "#ID { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple03a() {
		String xml = "<test>AAA<a style=\"color: #000000;\" id=\"ID\">BBB</a>CCC</test>";
		String css = "#ID { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a style=\"color: #000000;color:#FFFFFF;background-color:#008800;\" id=\"ID\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple03b() {
		String xml = "<test>AAA<a style=\"color: #000000;\" class=\"c1 c2\">BBB</a>CCC</test>";
		String css = ".c1 { color : #FFFFFF; } .c2 { background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a style=\"color: #000000;color:#FFFFFF;background-color:#008800;\" class=\"c1 c2\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple04() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC</test>";
		String css = "test a#ID { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple05() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC<a id=\"ID2\">BBB</a>DDD</test>";
		String css = "test > a { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC<a id=\"ID2\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>DDD</test>".equals(handler.toString()));
	}

	public void test_simple06() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC<div><a id=\"ID2\">BBB</a></div>DDD</test>";
		String css = "test > a { color : #FFFFFF; background-color: #008800; } test { color : #FF0000; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test style=\"color:#FF0000;\">AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC<div><a id=\"ID2\">BBB</a></div>DDD</test>".equals(handler.toString()));
	}

	public void test_simple07() {
		String xml = "<test>AAA<div><a>BBB</a></div>DD<a>EE</a></test>";
		String css = "test a { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<div><a style=\"color:#FFFFFF;background-color:#008800;\">BBB</a></div>DD<a style=\"color:#FFFFFF;background-color:#008800;\">EE</a></test>".equals(handler.toString()));
	}

	public void test_simple08() {
		String xml = "<test>AAA<a>BBB</a>DD<a>EE</a></test>";
		String css = "test > a:first-child { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>DD<a>EE</a></test>".equals(handler.toString()));
	}

	public void test_simple09() {
		String xml = "<test>AAA<a>BBB</a>DD<a>EE</a></test>";
		String css = "* { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue(("<test style=\"color:#FFFFFF;background-color:#008800;\">AAA" +
				"<a style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>DD" +
				"<a style=\"color:#FFFFFF;background-color:#008800;\">EE</a></test>").equals(handler.toString()));
	}

	public void test_simple10() {
		String xml = "<test>AAA<a style=\"color: #000000;\" class=\"c1 c2\" value=\"a1 a2\">BBB</a>CCC</test>";
		String css = ".c1 { color : #FFFFFF; } .c2 { background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a style=\"color: #000000;color:#FFFFFF;background-color:#008800;\" class=\"c1 c2\" value=\"a1 a2\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple11() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\">";
		String css = ".c1 { color : #FFFFFF; } .c2 { background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("".equals(handler.toString()));
	}

	public void test_simple12() {
		String xml = "<!DOCTYPE html PUBLIC \"-//i-mode group (ja)//DTD XHTML i-XHTML(Locale/Ver.=ja/2.3) 1.0//EN\" \"i-xhtml_4ja_10.dtd\">";
		String css = ".c1 { color : #FFFFFF; } .c2 { background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("".equals(handler.toString()));
	}

	public void test_simple13() {
		String xml = "<test>AAA<a value>BBB</a>CCC</test>";
		CSSExpandHandler handler = new CSSExpandHandler(null);
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a value=\"value\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple14() {
		String xml = "<test>AAA<a value=\"\">BBB</a>CCC</test>";
		CSSExpandHandler handler = new CSSExpandHandler(null);
		XhtmlParser parser = new XhtmlParser();
		parser.parse(xml.toCharArray(), handler);
		assertTrue("<test>AAA<a value=\"\">BBB</a>CCC</test>".equals(handler.toString()));
	}

}
