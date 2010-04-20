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
