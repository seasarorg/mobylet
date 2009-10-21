package org.mobylet.core.config.enums;

public enum ConfigKeyword {


	CONTEXT_PATH("contextPath"),

	DOC_BASE("docBase");


	private String keyword;

	private ConfigKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}
