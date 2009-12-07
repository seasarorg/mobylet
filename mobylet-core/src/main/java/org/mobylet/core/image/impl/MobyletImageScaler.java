package org.mobylet.core.image.impl;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageClipRectangle;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.image.ScaleType;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.SingletonUtils;

public class MobyletImageScaler implements ImageScaler {

	/**
	 * HeadlessExceptionが発生する環境かどうかのFLAG
	 */
	protected boolean isHeadless = false;

	@Override
	public void scale(InputStream imgStream, OutputStream outStream,
			ImageCodec imageCodec, Integer newWidth, ScaleType scaleType) {
		try {
			//CalcRectangle
			BufferedImage img = ImageIO.read(imgStream);
			if (newWidth == null) {
				ImageConfig config = SingletonUtils.get(ImageConfig.class);
				String defWidth = null;
				if ((defWidth = config.getDefaultScaleImageWidth()) != null) {
					newWidth = Integer.parseInt(defWidth);
				} else {
					newWidth = img.getWidth();
				}
			}
			ImageClipRectangle rectangle =
				getClipRectangle(img.getWidth(), img.getHeight(), newWidth, scaleType);
			int scaledWidth = rectangle.getWidth();
			int scaledHeight = rectangle.getHeight();
			if (scaleType == ScaleType.CLIPSQUARE) {
				scaledWidth = newWidth;
				scaledHeight = newWidth;
			}
			BufferedImage outImgBuf = null;
			//NewScaledImage
			if(img.getColorModel() instanceof IndexColorModel) {
				if (imageCodec == ImageCodec.GIF &&
						img.getColorModel().hasAlpha()) {
					if (!isHeadless) {
						try {
							outImgBuf =
								GraphicsEnvironment
								.getLocalGraphicsEnvironment()
								.getDefaultScreenDevice()
								.getDefaultConfiguration()
								.createCompatibleImage(
										scaledWidth, scaledHeight, Transparency.BITMASK);
						} catch (HeadlessException e) {
							isHeadless = true;
						}
					}
					if (isHeadless) {
						outImgBuf =
							new BufferedImage(
									scaledWidth, scaledHeight, img.getType(),
									(IndexColorModel)img.getColorModel());
					}
				} else {
					outImgBuf =
						new BufferedImage(
								scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
				}
			} else {
				if(img.getType() == 0) {
					outImgBuf =
						new BufferedImage(
								scaledWidth, scaledHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
				} else {
					outImgBuf =
						new BufferedImage(
								scaledWidth, scaledHeight, img.getType());
				}
			}
			//ClipImage
			if (scaleType == ScaleType.CLIPSQUARE) {
				boolean isLongWidth = img.getWidth() > img.getHeight();
				int clipWidth = isLongWidth ?
						img.getHeight() : img.getWidth();
				img = img.getSubimage(
						(isLongWidth ? (img.getWidth() - clipWidth) / 2 : 0),
						(isLongWidth ? 0 : (img.getHeight() - clipWidth) / 2),
						clipWidth,
						clipWidth);
			}
			//AsyncLoad
			Image scaledImg =
				img.getScaledInstance(
						scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
			//WaitLoading
			/*
			MediaTracker tracker = new MediaTracker(new Canvas());
			tracker.addImage(scaledImg, 0);
			try {
				tracker.waitForAll();
			} catch (InterruptedException e) {
				throw new MobyletRuntimeException("画像変換に失敗", e);
			}
			*/
			//GetGraphics
			Graphics g = outImgBuf.getGraphics();
			try {
				g.drawImage(
						scaledImg, 0, 0, scaledWidth, scaledHeight, null);
			} finally {
				g.dispose();
			}
			//WriteImageToOutputStream
			boolean result =
				ImageIO.write(outImgBuf, imageCodec.name(), outStream);
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
			Integer width, Integer height, Integer newWidth, ScaleType scaleType) {
		return ImageUtils.getClipRectangle(width, height, newWidth, scaleType);
	}

}
