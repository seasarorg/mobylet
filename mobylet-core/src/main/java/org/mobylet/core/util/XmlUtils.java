package org.mobylet.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mobylet.core.MobyletInitializeException;
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
		} catch (FileNotFoundException e) {
			throw new MobyletInitializeException(
					"ファイルが存在しません [" + path + "]", e);
		} catch (URISyntaxException e) {
			throw new MobyletInitializeException(
					"URI参照出来ません [" + path + "]", e);
		} catch (ParserConfigurationException e) {
			throw new MobyletInitializeException(
					"重大な構成エラーがあります [" + path + "]", e);
		} catch (SAXException e) {
			throw new MobyletInitializeException(
					"SAXパース中にエラーが発生しました [" + path + "]", e);
		} catch (IOException e) {
			throw new MobyletInitializeException(
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
