/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.mobylet.mail.builder.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.builder.HtmlPart;
import org.mobylet.mail.builder.MobyletMailBuilder;
import org.mobylet.mail.message.MobyletMessage;
import org.mobylet.mail.message.MessageBody.Attach;
import org.mobylet.mail.util.DataHandlerUtils;
import org.mobylet.mail.util.PartUtils;

public class MobyletHtmlMailBuilder implements MobyletMailBuilder, MailConstants {

	@Override
	public MobyletMessage build(MobyletMessage message) {
		return buildHtmlMail(message);
	}

	public MobyletMessage buildHtmlMail(MobyletMessage message) {
		MimeMultipart multipart = buildHtmlMultipart(message);
		if (message.getBody().getAttaches().size() > 0) {
			try {
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
		}
		try {
			message.setContent(multipart);
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("メッセージ構築時に例外発生", e);
		}
		return message;
	}

	public MimeMultipart buildHtmlMultipart(MobyletMessage message) {
		HtmlPart htmlPart = PartUtils.buildHtmlPart(
				message.getCarrier(),
				message.getBody().getHtml());
		MimeMultipart multipart = new MimeMultipart();
		try {
			multipart.setSubType(RELATED);
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setDataHandler(
					DataHandlerUtils.getDataHandler(
							message.getCarrier(),
							htmlPart.getSource()));
			bodyPart.addHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			bodyPart.addHeader(CONTENT_TYPE,
					TEXT_HTML + "; charset=\"" + message.getNotifyCharset() + "\"");
			multipart.addBodyPart(bodyPart);
			if (htmlPart.getInlineParts() != null && htmlPart.getInlineParts().size() > 0) {
				for (MimeBodyPart inlinePart : htmlPart.getInlineParts()) {
					multipart.addBodyPart(inlinePart);
				}
			}
			return multipart;
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("パートの設定に失敗", e);
		}
	}


}
