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
