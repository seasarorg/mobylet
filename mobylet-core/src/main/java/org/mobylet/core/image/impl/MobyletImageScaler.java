package org.mobylet.core.image.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.image.ImageCodec;

public class MobyletImageScaler implements ImageScaler {

	@Override
	public void resize(InputStream imgStream, OutputStream scaledImage,
			ImageCodec codec, Integer width, Integer height) {
		try {
			BufferedImage bufImage = ImageIO.read(imgStream);
			int imgWidth = bufImage.getWidth();
			int imgHeight = bufImage.getHeight();
			if (width != null && height != null) {
				imgWidth = width;
				imgHeight = height;
			} else if (width != null && height == null) {
				imgHeight = (int)(imgHeight * (double)width/(double)imgWidth);
				imgWidth = width;
			} else if (width == null && height != null) {
				imgWidth = (int)(imgWidth * (double)height/(double)imgHeight);
				imgHeight = height;
			}
			BufferedImage rescaledImage =
				new BufferedImage(imgWidth, imgHeight, bufImage.getType());
			rescaledImage.getGraphics().drawImage(
					bufImage.getScaledInstance(
							imgWidth, imgHeight, Image.SCALE_AREA_AVERAGING),
							0, 0, imgWidth, imgHeight, null);
			boolean result =
				ImageIO.write(rescaledImage, codec.name(), scaledImage);
			if (!result) {
				throw new MobyletRuntimeException("画像の書き出しに失敗", null);
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException("画像変換中にIO例外が発生", e);
		}
	}

}
