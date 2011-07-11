/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.util.Date;

public class AnalyticsSession {

	protected Date firstTm;

	protected Date previousTm;

	protected Date currentTm;

	protected Date accessTm;

	protected Integer visitCount;

	public AnalyticsSession() {
		firstTm = new Date();
		previousTm = new Date();
		currentTm = new Date();
		accessTm = new Date();
		visitCount = 1;
	}

	public Date getFirstTm() {
		return firstTm;
	}

	public String getFirstTmString() {
		return String.valueOf(firstTm.getTime() / 1000);
	}

	public void setFirstTm(Date firstTm) {
		this.firstTm = firstTm;
	}

	public Date getPreviousTm() {
		return previousTm;
	}

	public String getPreviousTmString() {
		return String.valueOf(previousTm.getTime() / 1000);
	}

	public void setPreviousTm(Date previousTm) {
		this.previousTm = previousTm;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Date getCurrentTm() {
		return currentTm;
	}

	public String getCurrentTmString() {
		return String.valueOf(currentTm.getTime() / 1000);
	}

	public void setCurrentTm(Date currentTm) {
		this.currentTm = currentTm;
	}

	public Date getAccessTm() {
		return accessTm;
	}

	public void touch() {
		accessTm = new Date();
	}
}
