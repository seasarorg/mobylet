package org.mobylet.core.analytics;

public class AnalyticsSearchEngine {

	protected SearchEngineType type;

	protected String encodedSearchWords = "";


	public SearchEngineType getType() {
		return type;
	}

	public void setType(SearchEngineType type) {
		this.type = type;
	}

	public String getEncodedSearchWords() {
		return encodedSearchWords;
	}

	public void setEncodedSearchWords(String searchWords) {
		this.encodedSearchWords = searchWords;
	}

	public String getUTMCSR() {
		if (type == null) {
			return "(direct)";
		}
		switch (type) {
		case YAHOO_MOBILE:
			return "yahoo-mobile";
		case GOOGLE_MOBILE:
			return "google-mobile";
		case EZWEB:
			return "ezweb-search";
		}
		return "(direct)";
	}

	public String getUTMCMD() {
		if (type == null) {
			return "direct";
		} else {
			return "organic";
		}
	}

	public enum SearchEngineType {

		YAHOO_MOBILE,

		GOOGLE_MOBILE,

		EZWEB;

	}

}
