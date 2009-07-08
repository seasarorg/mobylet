package org.mobylet.view.design;

public class GpsDesign {

	protected String url;

	protected TagAttribute tagAttribute;


	public GpsDesign(String url) {
		this.url = url;
	}

	public GpsDesign(String url, TagAttribute tagAttribute) {
		this(url);
		this.tagAttribute = tagAttribute;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TagAttribute getTagAttribute() {
		return tagAttribute;
	}

	public void setTagAttribute(TagAttribute tagAttribute) {
		this.tagAttribute = tagAttribute;
	}


}
