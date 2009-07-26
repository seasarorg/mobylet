package org.mobylet.core.config;

public enum MobyletReservedKeyword {


	CONTEXT_PATH("contextPath");


	private String keyword;

	private MobyletReservedKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}
