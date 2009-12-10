package org.mobylet.view.design;

import org.mobylet.view.xhtml.TagAttribute;


public class AccesskeyAnchorDesign {

	protected String url;

	protected String emojiString;

	protected TagAttribute tagAttribute;


	public AccesskeyAnchorDesign(String url) {
		this.url = url;
	}

	public AccesskeyAnchorDesign(String url, TagAttribute tagAttribute) {
		this(url);
		this.tagAttribute = tagAttribute;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmojiString() {
		return emojiString;
	}

	public void setEmojiString(String emoji) {
		this.emojiString = emoji;
	}

	public TagAttribute getTagAttribute() {
		return tagAttribute;
	}

	public void setTagAttribute(TagAttribute tagAttribute) {
		this.tagAttribute = tagAttribute;
	}


}
