package org.mobylet.mail.message;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mobylet.core.util.ResourceUtils;

public class MessageBody {

	protected String text;

	protected String html;

	protected List<Attach> attaches;


	public MessageBody() {
		this.attaches = new ArrayList<Attach>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Attach addAttachPath(String path) {
		Attach attach = new Attach(
				path, ResourceUtils.getResourceFileOrInputStream(path));
		this.attaches.add(attach);
		return attach;
	}

	public Attach addAttachStream(String filename, InputStream inputStream) {
		Attach attach = new Attach(filename, inputStream);
		this.attaches.add(attach);
		return attach;
	}

	public List<Attach> getAttaches() {
		return attaches;
	}


	public static class Attach {

		protected String realPath;

		protected String nickname;

		protected InputStream inputStream;

		public Attach(String realPath, InputStream inputStream) {
			this(realPath,
					realPath != null && realPath.indexOf(File.separator) >= 0 ?
							realPath.substring(realPath.lastIndexOf(File.separator)+1) :
								realPath,
					inputStream);
		}

		public Attach(String realPath, String nickName, InputStream inputStream) {
			this.realPath = realPath;
			this.nickname = nickName;
			this.inputStream = inputStream;
		}

		public String getRealPath() {
			return realPath;
		}

		public void setRealPath(String realPath) {
			this.realPath = realPath;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public InputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

	}

}
