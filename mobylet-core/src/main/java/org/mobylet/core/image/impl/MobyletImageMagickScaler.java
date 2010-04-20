/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.mobylet.core.image.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageClipRectangle;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.image.ScaleType;
import org.mobylet.core.log.MobyletLogger;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.InputToOutputStreamUtils;
import org.mobylet.core.util.OutputStreamUtils;
import org.mobylet.core.util.SingletonUtils;

public class MobyletImageMagickScaler implements ImageScaler {

	@Override
	public void scale(InputStream imgStream, OutputStream outImage,
			ImageCodec imageCodec, Integer newWidth, ScaleType scaleType) {
		//No Width
		if (newWidth == null) {
			try {
				InputToOutputStreamUtils.writeAll(imgStream, outImage);
				return;
			} finally {
				InputStreamUtils.closeQuietly(imgStream);
			}
		}
		//Config Parameter
		ImageConfig config = SingletonUtils.get(ImageConfig.class);
		String workDir = config.getImageMagickWorkDir();
		String convertPath = config.getImageMagickPath();
		String command = null;
		//File I/O
		FileOutputStream workFOS = null;
		FileInputStream workFIS = null;
		String workSrcFilePath = workDir + UUID.randomUUID().toString();
		String workDstFilePath = workDir + UUID.randomUUID().toString();
		File workSrcFile = new File(workSrcFilePath);
		File workDstFile = new File(workDstFilePath);
		try {
			//Write SrcWorkFile
			workFOS = new FileOutputStream(workSrcFile);
			InputToOutputStreamUtils.writeAll(imgStream, workFOS);
			//Witch ScaleType
			switch (scaleType) {
			case FITWIDTH:
				command = convertPath + "convert -geometry " + newWidth + " " +
						workSrcFilePath + " " + workDstFilePath;
				break;
			case INSQUARE:
				command = convertPath + "convert -geometry " + newWidth + "x" + newWidth + " " +
						workSrcFilePath + " " + workDstFilePath;
				break;
			case CLIPSQUARE:
				command = convertPath + "convert -gravity center -crop " + newWidth + "x" + newWidth + "+0+0 " +
						workSrcFilePath + " " + workDstFilePath;
				break;
			}
			//Execute
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(command);
			process.waitFor();
			if (process.exitValue() != 0) {
				InputStream errorStream = process.getErrorStream();
				String errorInfo = new String(InputStreamUtils.getAllBytes(errorStream));
				throw new MobyletRuntimeException(
						"画像変換中に例外発生 [" + errorInfo + "]", null);
			}
			//Write OutputStream
			workFIS = new FileInputStream(workDstFile);
			InputToOutputStreamUtils.writeAll(workFIS, outImage);
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"画像変換中にI/O例外発生", e);
		} catch (InterruptedException e) {
			throw new MobyletRuntimeException(
					"画像変換中に割り込み例外発生", e);
		} finally {
			//Close Stream
			OutputStreamUtils.closeQuietly(workFOS);
			InputStreamUtils.closeQuietly(workFIS);
			//Remove File
			boolean isSrcSuccess = workSrcFile.delete();
			if (!isSrcSuccess) {
				MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
				if (logger != null && logger.isLoggable())
					logger.log("[mobylet] ImageMagick変換前ワークファイルを削除出来ませんでした = " + workSrcFile.getAbsolutePath());
			}
			boolean isDstSuccess = workDstFile.delete();
			if (!isDstSuccess) {
				MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
				if (logger != null && logger.isLoggable())
					logger.log("[mobylet] ImageMagick変換後ワークファイルを削除出来ませんでした = " + workDstFile.getAbsolutePath());
			}
		}
	}

	@Override
	public ImageClipRectangle getClipRectangle(
			Integer width, Integer height, Integer newWidth, ScaleType scaleType) {
		return null;
	}

}
