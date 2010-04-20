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
