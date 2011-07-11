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
		
		protected boolean isInline;

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
			this.isInline = false;
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

		public boolean isInline() {
			return isInline;
		}

		public void setInline(boolean isInline) {
			this.isInline = isInline;
		}

	}

}
