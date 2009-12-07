package org.mobylet.view.css;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;


public class CSSExpandHandlerTest extends TestCase {

	public void test_simple01() {
		String xml = "<test>AAA<a>BBB</a>CCC</test>";
		CSSExpandHandler handler = new CSSExpandHandler(null);
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();
			parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(xml.equals(handler.toString()));
	}

	public void test_simple02() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC</test>";
		CSSExpandHandler handler = new CSSExpandHandler(null);
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();
			parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(xml.equals(handler.toString()));
	}

	public void test_simple03() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC</test>";
		String css = "#ID { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();
			parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("<test>AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple04() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC</test>";
		String css = "test a#ID { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();
			parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("<test>AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC</test>".equals(handler.toString()));
	}

	public void test_simple05() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC<a id=\"ID2\">BBB</a>DDD</test>";
		String css = "test > a { color : #FFFFFF; background-color: #008800; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();
			parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("<test>AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC<a id=\"ID2\">BBB</a>DDD</test>".equals(handler.toString()));
	}

	public void test_simple06() {
		String xml = "<test>AAA<a id=\"ID\">BBB</a>CCC<a id=\"ID2\">BBB</a>DDD</test>";
		String css = "test > a { color : #FFFFFF; background-color: #008800; } test { color : #FF0000; }";
		CSSParser cssParser = new CSSParser();
		CSSExpandHandler handler = new CSSExpandHandler(
				cssParser.parse(new ByteArrayInputStream(css.getBytes())));
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();
			parser.parse(new ByteArrayInputStream(xml.getBytes()), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("<test style=\"color:#FF0000;\">AAA<a id=\"ID\" style=\"color:#FFFFFF;background-color:#008800;\">BBB</a>CCC<a id=\"ID2\">BBB</a>DDD</test>".equals(handler.toString()));
	}
}