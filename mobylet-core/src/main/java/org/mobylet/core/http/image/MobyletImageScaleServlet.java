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
package org.mobylet.core.http.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.http.MobyletServletOutputStream;
import org.mobylet.core.http.MobyletResponse.Ready;
import org.mobylet.core.image.ConnectionStream;
import org.mobylet.core.image.ImageCacheHelper;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageReader;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletImageScaleServlet extends HttpServlet {

	private static final long serialVersionUID = -8330083988206718597L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//GetPath
		String path = req.getParameter(ImageConfig.PKEY_IMGPATH);
		//DoScale
		doScale(path, false, req, resp);
	}

	protected void doScale(String path, boolean isReplace,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (StringUtils.isEmpty(path)) {
			return;
		}
		//GetImageCodec
		ImageCodec codec = ImageUtils.getImageCodec(path);
		//Helpers
		ImageReader imageReader = SingletonUtils.get(ImageReader.class);
		ImageScaler imageScaler = SingletonUtils.get(ImageScaler.class);
		ImageCacheHelper cacheHelper = SingletonUtils.get(ImageCacheHelper.class);
		boolean enableCache = cacheHelper.enableCache();
		//---------------------------------------------------------------------
		// Read-Image
		//---------------------------------------------------------------------
		//ConvertPath
		path = imageReader.constructPath(path);
		//ImageInputStream
		ConnectionStream imageConnectionStream;
		OutputStream outStream = null;
		//CacheKey
		String key = null;
		//EnableCache
		if (enableCache) {
			//GetKey
			key = cacheHelper.getCacheKey(path);
			//ExsistsCache
			if (cacheHelper.existsCache(key)) {
				//ReturnCacheImage
				imageConnectionStream =
					new ConnectionStream(null, cacheHelper.get(key));
				byte[] imageBytes =
					InputStreamUtils.getAllBytes(
							imageConnectionStream.getInputStream());
				//ReCompute Codec
				codec = ImageUtils.getImageCodec(imageBytes);
				RequestUtils.getMobyletContext().set(new Ready());
				resp.setContentType(ImageUtils.getContentTypeString(codec));
				resp.setContentLength(imageBytes.length);
				resp.getOutputStream().write(imageBytes);
				resp.flushBuffer();
				return;
			}
			//NotExistsCache
			else {
				imageConnectionStream = imageReader.getStream(path);
			}
			if (imageConnectionStream != null) {
				//ReComputeCodec
				codec = getComputeCodec(codec, imageConnectionStream);
				outStream = new ByteArrayOutputStream(4096);
			}
		}
		//UnEnableCache
		else {
			imageConnectionStream = imageReader.getStream(path);
			if (imageConnectionStream != null) {
				RequestUtils.getMobyletContext().set(new Ready());
				//ReComputeCodec
				codec = getComputeCodec(codec, imageConnectionStream);
				outStream = new MobyletServletOutputStream(resp.getOutputStream());
			}
		}
		//---------------------------------------------------------------------
		// Check-Stream
		//---------------------------------------------------------------------
		if (imageConnectionStream == null) {
			if (!isReplace) {
				ImageConfig config = SingletonUtils.get(ImageConfig.class);
				String replace404path = config.getReplace404();
				if (StringUtils.isNotEmpty(replace404path)) {
					doScale(replace404path, true, req, resp);
				}
			} else {
				resp.setContentType(ImageUtils.getContentTypeString(codec));
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			return;
		}
		//---------------------------------------------------------------------
		// Set-ContentType
		//---------------------------------------------------------------------
		RequestUtils.getMobyletContext().set(new Ready());
		resp.setContentType(ImageUtils.getContentTypeString(codec));
		//---------------------------------------------------------------------
		// Convert-Image
		//---------------------------------------------------------------------
		imageScaler.scale(
				imageConnectionStream.getInputStream(),
				outStream,
				codec,
				ImageUtils.getScaledWidth(),
				ImageUtils.getScaleType());
		//closeConnection
		if (imageConnectionStream.getConnection() != null) {
			imageConnectionStream.getConnection().disconnect();
		}
		//---------------------------------------------------------------------
		// WriteResponse
		//---------------------------------------------------------------------
		byte[] imageBytes = null;
		if (outStream instanceof ByteArrayOutputStream) {
			imageBytes =
				ByteArrayOutputStream.class.cast(outStream).toByteArray();
			if (imageBytes != null) {
				resp.setContentLength(imageBytes.length);
				resp.getOutputStream().write(imageBytes);
			}
			resp.flushBuffer();
			// WriteCache
			if (enableCache &&
					imageBytes != null &&
					StringUtils.isNotEmpty(key)) {
				cacheHelper.put(key, new ByteArrayInputStream(imageBytes));
			}
		} else if (outStream instanceof MobyletServletOutputStream) {
			MobyletServletOutputStream msos =
				MobyletServletOutputStream.class.cast(outStream);
			resp.setContentLength(msos.getLength());
			resp.flushBuffer();
		}
	}
	
	protected ImageCodec getComputeCodec(ImageCodec codec, ConnectionStream connectionStream) {
		if (codec == null) {
			codec = ImageUtils.getImageCodec(connectionStream);
			//Force Setting. #Deprecated Process
			if (codec == null) {
				codec = ImageCodec.JPG;
			}
		}
		return codec;
	}
	
}
