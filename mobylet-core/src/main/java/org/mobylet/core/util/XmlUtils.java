package org.mobylet.core.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mobylet.core.MobyletRuntimeException;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlUtils {

	public static void parseSax(String path, DefaultHandler handler) {
		if (StringUtils.isEmpty(path)) {
			return;
		}
		InputStream xmlStream = null;
		try {
			xmlStream = ResourceUtils.getResourceFileOrInputStream(path);
			if (xmlStream != null) {
				SAXParserFactory spfactory = SAXParserFactory.newInstance();
				SAXParser parser = spfactory.newSAXParser();
				parser.parse(xmlStream, handler);
			}
		} catch (ParserConfigurationException e) {
			throw new MobyletRuntimeException(
					"重大な構成エラーがあります [" + path + "]", e);
		} catch (SAXException e) {
			throw new MobyletRuntimeException(
					"SAXパース中にエラーが発生しました [" + path + "]", e);
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"IO例外が発生しました [" + path + "]", e);
		} finally {
			if (xmlStream != null) {
				try {
					xmlStream.close();
				} catch (Exception e) {
					//NOP
				}
			}
		}
	}
}
