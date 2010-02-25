package org.mobylet.core.config;

import java.util.List;

import junit.framework.TestCase;

import org.mobylet.core.config.xml.MobyletSessionConfigXml;
import org.mobylet.core.xml.Xml;
import org.mobylet.core.xml.XmlNode;


public class MobyletSessionConfigTest extends TestCase implements MobyletSessionConfigXml {

	public void test_initialize() {
		Xml mobyletSessionXml = new Xml("mobyletSession.xml");
		XmlNode root = mobyletSessionXml.getRootNode();
		List<XmlNode> list = root.getNodeListByXPath(X_DTB_RH_HOST);
		for (XmlNode node : list) {
			System.out.println(node.getAttributes().get("name") + " " + node.getValue());
		}
	}
}
