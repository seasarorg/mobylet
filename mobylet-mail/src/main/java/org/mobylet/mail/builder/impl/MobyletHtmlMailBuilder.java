package org.mobylet.mail.builder.impl;

import javax.mail.MessagingException;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.builder.MobyletMailBuilder;
import org.mobylet.mail.message.MobyletMessage;
import org.mobylet.mail.util.DataHandlerUtils;

public class MobyletHtmlMailBuilder implements MobyletMailBuilder, MailConstants {

	@Override
	public MobyletMessage build(MobyletMessage message) {
		if (message.getBody().getAttaches().size() == 0) {
			return buildSimpleHtmlMail(message);
		} else {
			return buildAttachedHtmlMail(message);
		}
	}

	public MobyletMessage buildSimpleHtmlMail(MobyletMessage message) {
		try {
			message.setDataHandler(
					DataHandlerUtils.getDataHandler(
							message.getCarrier(),
							message.getBody().getHtml()));
			message.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			message.addHeader(CONTENT_TYPE,
					TEXT_HTML + "; charset=\"" + message.getNotifyCharset() + "\"");
			return message;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}

	public MobyletMessage buildAttachedHtmlMail(MobyletMessage message) {
		return message;
	}

}
