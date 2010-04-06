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
