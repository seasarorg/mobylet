package org.mobylet.core.analytics;

public interface AnalyticsHelper {

	public AnalyticsParameters prepare(String id);

	public String getURL(AnalyticsParameters parameters);

}
