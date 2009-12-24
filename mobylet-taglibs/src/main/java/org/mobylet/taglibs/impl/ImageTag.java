package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.ImageDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class ImageTag extends MobyletDynamicSimpleTagSupport {

	public static final String TAG = "img";

	public static ImageConfig config = SingletonUtils.get(ImageConfig.class);


	protected String src = "";

	protected String magniWidth = "";

	protected String scaleType = "";

	protected String codec = "";

	protected String useFilter = "";


	@Override
	public void doTag() throws JspException, IOException {
		try {
			ImageDesigner designer =
				SingletonDesigner.getDesigner(ImageDesigner.class);
			String imgSrc = designer.getSrc(
					src,
					StringUtils.isEmpty(magniWidth) ?
							1.0 : Double.parseDouble(magniWidth),
					ImageUtils.getScaleType(scaleType),
					(codec == null ? null : ImageCodec.valueOf(codec)),
					"TRUE".equalsIgnoreCase(useFilter));
			addAttribute("src", imgSrc);
			JspWriterUtils.write(
					getJspContext().getOut(),
					STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getMagniWidth() {
		return magniWidth;
	}

	public void setMagniWidth(String magniWidth) {
		this.magniWidth = magniWidth;
	}

	public String getScaleType() {
		return scaleType;
	}

	public void setScaleType(String scaleType) {
		this.scaleType = scaleType;
	}

	public String getCodec() {
		return codec;
	}

	public void setCodec(String codec) {
		this.codec = codec;
	}

	public String getUseFilter() {
		return useFilter;
	}

	public void setUseFilter(String useFilter) {
		this.useFilter = useFilter;
	}

}
