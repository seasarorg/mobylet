package org.mobylet.mail.builder.impl;

import javax.mail.MessagingException;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.builder.MobyletMailBuilder;
import org.mobylet.mail.message.MobyletMessage;
import org.mobylet.mail.util.DataHandlerUtils;

public class MobyletTextMailBuilder implements MobyletMailBuilder, MailConstants {

	@Override
	public MobyletMessage build(MobyletMessage message) {
		if (message.getBody().getAttaches().size() == 0) {
			return buildSimpleTextMail(message);
		} else {
			return buildAttachedTextMail(message);
		}
	}

	public MobyletMessage buildSimpleTextMail(MobyletMessage message) {
		try {
			message.setDataHandler(
					DataHandlerUtils.getDataHandler(
							message.getCarrier(),
							message.getBody().getText()));
			message.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			message.addHeader(CONTENT_TYPE,
					TEXT_PLAIN + "; charset=\"" + message.getNotifyCharset() + "\"");
			return message;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}

	public MobyletMessage buildAttachedTextMail(MobyletMessage message) {
		return message;
	}
}
