package org.mobylet.core.image.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.http.MobyletServletOutputStream;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageScaler;

public class MobyletImageScaler implements ImageScaler {

	@Override
	public int resize(InputStream imgStream, MobyletServletOutputStream outputStream,
			ImageCodec codec, Integer width, Integer height) {
		try {
			BufferedImage sourceImage = ImageIO.read(imgStream);
			int imgWidth = sourceImage.getWidth();
			int imgHeight = sourceImage.getHeight();
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
			//Dest-Image
			BufferedImage rescaledImage = null;
			if(sourceImage.getColorModel() instanceof IndexColorModel ) {
				rescaledImage =
					new BufferedImage(
							imgWidth,
							imgHeight,
							sourceImage.getType(),
							(IndexColorModel)sourceImage.getColorModel());
			} else {
				if(sourceImage.getType() == 0) {
					rescaledImage =
						new BufferedImage(
								imgWidth,
								imgHeight,
								BufferedImage.TYPE_4BYTE_ABGR_PRE);
				} else {
					rescaledImage =
						new BufferedImage(
								imgWidth, imgHeight, sourceImage.getType());
				}
			}
			//Alpha-Setting
			if(rescaledImage.getColorModel().hasAlpha() &&
					rescaledImage.getColorModel() instanceof IndexColorModel) {
				int transparentPixel =
					((IndexColorModel)rescaledImage.getColorModel()).getTransparentPixel();
				for(int i=0; i<rescaledImage.getWidth(); ++i) {
					for(int j=0; j<rescaledImage.getHeight(); ++j) {
						rescaledImage.setRGB(i, j, transparentPixel);
					}
				}
			}
			//Draw
			rescaledImage.getGraphics().drawImage(
					sourceImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_AREA_AVERAGING),
					0, 0, imgWidth, imgHeight, null);
			boolean result =
				ImageIO.write(rescaledImage, codec.name(), outputStream);
			if (!result) {
				throw new MobyletRuntimeException("画像の書き出しに失敗", null);
			}
			return outputStream.getLength();
		} catch (IOException e) {
			throw new MobyletRuntimeException("画像変換中にIO例外が発生", e);
		} finally {
			try {
				if (imgStream != null) {
					imgStream.close();
				}
			} catch (Exception e) {
				//NOP
			}
		}
	}

}
