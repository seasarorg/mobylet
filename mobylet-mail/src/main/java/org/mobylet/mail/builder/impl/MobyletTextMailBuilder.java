package org.mobylet.mail.builder.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.builder.MobyletMailBuilder;
import org.mobylet.mail.message.MobyletMessage;
import org.mobylet.mail.message.MessageBody.Attach;
import org.mobylet.mail.util.DataHandlerUtils;
import org.mobylet.mail.util.PartUtils;

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
		return buildTextPart(
				message,
				message.getCarrier(),
				message.getBody().getText(),
				message.getNotifyCharset());
	}

	public MobyletMessage buildAttachedTextMail(MobyletMessage message) {
		MimeMultipart multipart =
			createAttachMultipart(message.getCarrier());
		MimeBodyPart part = buildTextPart(
				new MimeBodyPart(),
				message.getCarrier(),
				message.getBody().getText(),
				message.getNotifyCharset());
		try {
			multipart.addBodyPart(part);
			for (Attach attach : message.getBody().getAttaches()) {
				multipart.addBodyPart(
						PartUtils.buildAttachPart(
								message.getCarrier(),
								attach)
						);
			}
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("メッセージ構築時に例外発生", e);
		}
		return message;
	}

	public <M extends MimePart> M buildTextPart(
			M part, Carrier carrier, String text, String notifyCharset) {
		try {
			part.setDataHandler(
					DataHandlerUtils.getDataHandler(
							carrier,
							text));
			part.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			part.addHeader(CONTENT_TYPE,
					TEXT_PLAIN + "; charset=\"" + notifyCharset + "\"");
			return part;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}

	public MimeMultipart createAttachMultipart(Carrier carrier) {
		MimeMultipart multiPart = new MimeMultipart();
		try {
			switch (carrier) {
			case DOCOMO:
			case AU:
			case OTHER:
				multiPart.setSubType(MIXED);
				break;
			case SOFTBANK:
				multiPart.setSubType(RELATED);
				break;
			}
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("Multipartが作成出来ません", e);
		}
		return multiPart;
	}
}
