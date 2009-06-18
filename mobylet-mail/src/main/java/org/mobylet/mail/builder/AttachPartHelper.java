package org.mobylet.mail.builder;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.config.MailMimeConfig;
import org.mobylet.mail.message.MessageBody.Attach;
import org.mobylet.mail.util.MailHeaderUtils;

public class AttachPartHelper implements MailConstants {

	public static MimeBodyPart buildAttachPart(Carrier carrier, Attach attach) {
		MimeBodyPart part = new MimeBodyPart();
		if (attach == null ||
				StringUtils.isEmpty(attach.getRealPath()) ||
				StringUtils.isEmpty(attach.getNickname()) ||
				attach.getInputStream() == null) {
			return part;
		}
		String realPath = attach.getRealPath();
		String extension = null;
		if (realPath.indexOf('.') >= 0) {
			extension = realPath.substring(realPath.lastIndexOf('.') + 1);
		}
		//Mime
		String mimeType = APPLICATION_OCTETSTREAM;
		if (StringUtils.isNotEmpty(extension)) {
			String defMime =
				SingletonUtils.get(MailMimeConfig.class)
				.getMimeProperties().getProperty(extension);
			if (StringUtils.isNotEmpty(defMime)) {
				mimeType = defMime;
			}
		}
		//DataBody
		DataSource byteSource = null;
		try {
			byteSource = new ByteArrayDataSource(
					attach.getInputStream(), APPLICATION_OCTETSTREAM
			);
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"入力ストリームからDataSourceが作成出来ません", e);
		}
		try {
			part.setDataHandler(new DataHandler(byteSource));
			part.setHeader(TRANSFER_ENCODING, ENCODING_BASE64);
			part.addHeader(CONTENT_DISPOSITION, DISPOSITION_ATTACHMENT);
			part.addHeader(
					CONTENT_TYPE, mimeType + "; name=\"" +
					MailHeaderUtils.encodeHeaderString(carrier, attach.getNickname())+
					"\""
			);
		} catch (MessagingException e) {
			throw new MobyletRuntimeException(
					"添付ファイルパートを作成中に例外発生", e);
		}
		return part;
	}

	public static MimeMultipart createAttachMultipart(Carrier carrier) {
		MimeMultipart multiPart = new MimeMultipart();
		try {
			multiPart.setSubType(MIXED);
		} catch (MessagingException e) {
			throw new MobyletRuntimeException("Multipartが作成出来ません", e);
		}
		return multiPart;
	}

}
