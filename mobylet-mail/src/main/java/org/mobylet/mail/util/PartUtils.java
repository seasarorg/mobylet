package org.mobylet.mail.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimePart;
import javax.mail.util.ByteArrayDataSource;

import org.mobylet.core.Carrier;
import org.mobylet.mail.MobyletMailConfg;

public class PartUtils implements MobyletMailConfg {

	public static MimePart constructTextPart(
			Carrier carrier, MimePart part, String text) {
		if (text == null) {
			text = "";
		}
		try {
			ByteBuffer buf =
				ByteBuffer.wrap(text.getBytes(CHARSET_ISO_2022_1));
			DataSource source =
				new ByteArrayDataSource(
						buf.array(), APPLICATION_OCTETSTREAM
				);
			part.setDataHandler(new DataHandler(source));
			part.setHeader(TRANSFER_ENCODING, ENCODING_7BIT);
			part.setHeader(CONTENT_TYPE,
					TEXT_PLAIN + "; charset=\"" + CHARSET_JIS_STANDARD + "\"");
			return part;
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
