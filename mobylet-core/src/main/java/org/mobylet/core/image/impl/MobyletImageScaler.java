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
import org.mobylet.core.image.ImageDimension;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.util.InputStreamUtils;

public class MobyletImageScaler implements ImageScaler {

	@Override
	public void scale(InputStream imgStream, OutputStream outImage, ImageCodec type, int newWidth) {
		try {
			BufferedImage img = ImageIO.read(imgStream);
			ImageDimension newSize = getNewSize(img.getWidth(), img.getHeight(), newWidth);
			int width = newSize.getWidth();
			int height = newSize.getHeight();
			BufferedImage outImg = null;
			//NewScaledImage
			if(img.getColorModel() instanceof IndexColorModel ) {
				outImg =
					new BufferedImage(
							width, height, img.getType(),
							(IndexColorModel)img.getColorModel());
			} else {
				if(img.getType() == 0) {
					outImg =
						new BufferedImage(
								width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
				} else {
					outImg =
						new BufferedImage(
								width, height, img.getType());
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
					img.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING),
					0, 0, width, height, null);
			boolean result =
				ImageIO.write(outImg, type.name(), outImage);
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
	public ImageDimension getNewSize(int width, int height, int newWidth) {
		return new ImageDimension(
				newWidth,
				(int)(height * (double)newWidth/(double)width));
	}

}
