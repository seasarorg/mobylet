package org.mobylet.core.image;

public class ImageDimension {

	protected int width;

	protected int height;

	public ImageDimension() {
		//NOP
	}

	public ImageDimension(int w, int h) {
		width = w;
		height = h;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
