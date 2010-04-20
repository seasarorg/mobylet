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
package org.mobylet.core;

import org.mobylet.core.device.Device;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.type.ContentType;
import org.mobylet.core.type.SmartPhoneType;

public interface Mobylet {

	public Carrier getCarrier();

	public MobyletDialect getDialect();

	public Device getDevice();

	public boolean hasCookies();

	public String getUid();

	public String getGuid();

	public Gps getGps();

	public DeviceDisplay getDisplay();

	public void setContentType(ContentType contentType);

	public ContentType getContentType();

	public boolean isGatewayIp();

	public SmartPhoneType getSmartPhoneType();

}
