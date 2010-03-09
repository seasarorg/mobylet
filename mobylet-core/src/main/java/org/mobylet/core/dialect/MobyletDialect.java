/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.core.dialect;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.gps.Gps;


public interface MobyletDialect {

	public Carrier getCarrier();

	public Charset getCharset();

	public String getCharsetName();

	public String getCharacterEncodingCharsetName();

	public String getContentCharsetName();

	public Pattern getCarrierMatchRegex();

	public Pattern getDeviceMatchRegex();

	public String getContentTypeString();

	public String getXContentTypeString();

	public String getUid();

	public String getGuid();

	public Gps getGps();

	public DeviceDisplay getDeviceDisplayByRequestHeader();

	public boolean isGatewayIp();
}
