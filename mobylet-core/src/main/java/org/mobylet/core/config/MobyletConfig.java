package org.mobylet.core.config;

import org.mobylet.core.util.StringUtils;

public class MobyletConfig {

	public static final String RES_DEVICE_DIR = "device/";

	public static final String RES_EMOJI_DIR = "emoji/";

	protected String configDir;

	protected String deviceDir;

	protected String emojiDir;

	public MobyletConfig(String configDir) {
		this.configDir = configDir;
	}

	public String getDeviceDir() {
		if (StringUtils.isEmpty(deviceDir)) {
			return RES_DEVICE_DIR;
		} else {
			return deviceDir;
		}
	}

	public String getEmojiDir() {
		if (StringUtils.isEmpty(emojiDir)) {
			return RES_EMOJI_DIR;
		} else {
			return emojiDir;
		}
	}

	public String getConfigDir() {
		return this.configDir;
	}

	public void setDeviceDir(String deviceDir) {
		this.deviceDir = deviceDir;
	}

	public void setEmojiDir(String emojiDir) {
		this.emojiDir = emojiDir;
	}

}
