package org.mobylet.core.analytics;

public interface AnalyticsHelper {

	public AnalyticsParameters getParameters(String id);

	public String getURL(AnalyticsParameters parameters);

}
