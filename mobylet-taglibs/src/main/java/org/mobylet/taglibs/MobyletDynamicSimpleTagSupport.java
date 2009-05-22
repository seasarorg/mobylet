package org.mobylet.taglibs;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.taglibs.utils.AttributesUtils;

public abstract class MobyletDynamicSimpleTagSupport
	extends SimpleTagSupport implements DynamicAttributes, MobyletTag {

	protected Set<Attribute> dynAttributes;

	public MobyletDynamicSimpleTagSupport() {
		dynAttributes = new LinkedHashSet<Attribute>();
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		addAttribute(localName, value);
	}

	public void addAttribute(String key, Object value) {
		AttributesUtils.addAttribute(dynAttributes, key, value);
	}

	public StringBuilder getDynamicAttributesStringBuilder() {
		return AttributesUtils
			.getAttributesStringBuilder(dynAttributes);
	}

}
