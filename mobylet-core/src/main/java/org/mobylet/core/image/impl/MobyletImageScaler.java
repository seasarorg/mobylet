package org.mobylet.core.image.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageClipRectangle;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.image.ScaleType;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.InputStreamUtils;

public class MobyletImageScaler implements ImageScaler {

	@Override
	public void scale(InputStream imgStream, OutputStream outImage,
			ImageCodec imageCodec, int newWidth, ScaleType scaleType) {
		try {
			//CalcRectangle
			BufferedImage img = ImageIO.read(imgStream);
			ImageClipRectangle rectangle =
				getClipRectangle(img.getWidth(), img.getHeight(), newWidth, scaleType);
			int scaledWidth = rectangle.getWidth();
			int scaledHeight = rectangle.getHeight();
			int drawWidth = scaledWidth;
			int drawHeight = scaledHeight;
			if (scaleType == ScaleType.CLIPSQUARE) {
				drawWidth = newWidth;
				drawWidth = newWidth;
			}
			BufferedImage outImg = null;
			//NewScaledImage
			if(img.getColorModel() instanceof IndexColorModel ) {
				outImg =
					new BufferedImage(
							scaledWidth, scaledHeight, img.getType(),
							(IndexColorModel)img.getColorModel());
			} else {
				if(img.getType() == 0) {
					outImg =
						new BufferedImage(
								scaledWidth, scaledHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
				} else {
					outImg =
						new BufferedImage(
								scaledWidth, scaledHeight, img.getType());
				}
			}
			//Alpha-Setting
			if(outImg.getColorModel().hasAlpha() &&
					outImg.getColorModel() instanceof IndexColorModel) {
				int transparentPixel =
					((IndexColorModel)outImg.getColorModel()).getTransparentPixel();
				for(int i=0; i<outImg.getWidth(); ++i) {
					for(int j=0; j<outImg.getHeight(); ++j) {
						outImg.setRGB(i, j, transparentPixel);
					}
				}
			}
			//Draw
			outImg.getGraphics().drawImage(
					img.getScaledInstance(
							scaledWidth, scaledHeight, Image.SCALE_AREA_AVERAGING),
							rectangle.getX(), rectangle.getY(), drawWidth, drawHeight, null);
			boolean result =
				ImageIO.write(outImg, imageCodec.name(), outImage);
			if (!result) {
				throw new MobyletRuntimeException("画像の書き出しに失敗", null);
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException("画像変換中にIO例外が発生", e);
		} finally {
			InputStreamUtils.closeQuietly(imgStream);
		}
	}

	@Override
	public ImageClipRectangle getClipRectangle(
			int width, int height, int newWidth, ScaleType scaleType) {
		return ImageUtils.getClipRectangle(width, height, newWidth, scaleType);
	}

}
