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

	public void recycle() {
		dynAttributes.clear();
	}

}
