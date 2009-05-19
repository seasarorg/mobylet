package org.mobylet.core.device;

import java.util.HashMap;
import java.util.Map;

public class DeviceDisplay {

	protected Integer width;

	protected Integer height;

	protected Map<String, DeviceDisplay> anotherDisplay;


	public DeviceDisplay() {
		this.width = 0;
		this.height = 0;
	}

	public DeviceDisplay(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void putAnotherDisplay(String key, DeviceDisplay display) {
		if (anotherDisplay == null) {
			anotherDisplay = new HashMap<String, DeviceDisplay>();
		}
		anotherDisplay.put(key, display);
	}

	public DeviceDisplay getAnotherDisplay(String key) {
		if (anotherDisplay == null) {
			return null;
		}
		return anotherDisplay.get(key);
	}

}
