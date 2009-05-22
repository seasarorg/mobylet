package org.mobylet.taglibs;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.mobylet.taglibs.utils.AttributesUtils;

public abstract class MobyletDynamicBodyTagSupport
	extends BodyTagSupport implements DynamicAttributes, MobyletTag {

	private static final long serialVersionUID = 9001759288949115138L;

	protected Set<Attribute> dynAttributes;

	public MobyletDynamicBodyTagSupport() {
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
