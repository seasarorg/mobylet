package org.mobylet.core.config;

import java.util.Properties;

import org.mobylet.core.util.ConfigUtils;

public class MobyletProperties extends Properties {

	private static final long serialVersionUID = -1972969679683548110L;


	@Override
	public Object put(Object key, Object value) {
		return super.put(
				key,
				value != null ?
						ConfigUtils.parseValue(value.toString()) :
							value);
	}


}
