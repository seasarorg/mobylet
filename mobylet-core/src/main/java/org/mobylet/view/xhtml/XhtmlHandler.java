package org.mobylet.view.xhtml;


public interface XhtmlHandler {

	void startDocument();

	void endDocument();

	void startElement(String name, TagAttributes attributes, boolean existsBody);

	void endElement(String name);

	void characters(char[] ch, int start, int length);

}
