/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.core.impl;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.device.DevicePool;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.type.ContentType;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;

/**
 *
 * <p>アクセス端末に関する諸々の情報を取得する基点クラス.</p>
 * <p>
 * DIコンテナ等を使用する場合は本クラスをコンポーネント化（RequestScope）し、
 * 各要求を捌くクラス（Action/Controller等）にDIすることで利用可能。
 * </p>
 * <p>
 * 上記以外のフレームワークで利用する場合はMobyletFactoryクラスを利用し、
 * RequestScope上（<code>MobyletFilter</code>以下の処理）で
 * <code>getInstance()</code>することで
 * <code>Mobylet</code>インスタンスを取得することが可能。
 * </p>
 *
 * @author stakeuchi
 *
 */
public class MobyletImpl implements Mobylet {

	/**
	 * アクセス端末のキャリア
	 */
	protected Carrier carrier;

	/**
	 * キャリア依存部分を解決するためのダイアレクト
	 */
	protected MobyletDialect dialect;

	/**
	 * アクセス端末の端末固有情報
	 */
	protected Device device;

	/**
	 * レスポンスのContentType
	 */
	protected ContentType contentType;

	/**
	 * 端末固有情報のキャッシュフラグ
	 */
	protected boolean isPopedDevice = false;

	/**
	 * GPS情報取得フラグ
	 */
	protected boolean isPopedGps = false;


	/**
	 * <p>デフォルトコンストラクタ.</p>
	 * <p>
	 * このコンストラクタを外部から呼び出すことで
	 * <code>Mobylet</code>インスタンスを取得することも可能だが、
	 * <code>MobyletFactory</code>経由で取得することでRequestScopeにキャッシュし、
	 * 同一リクエスト内で、<code>Mobylet</code>インスタンスを複数回取得する場合の
	 * 過剰な処理を省くことが出来る。
	 * </p>
	 */
	public MobyletImpl() {
		initialize();
	}

	/**
	 * <p>キャリアを取得します.</p>
	 *
	 * @return	アクセスした端末のキャリア
	 */
	@Override
	public Carrier getCarrier() {
		return carrier;
	}

	/**
	 * <p>ダイアレクトを取得します.</p>
	 *
	 * @return	アクセスした端末のキャリア依存部分を解決するダイアレクト
	 */
	@Override
	public MobyletDialect getDialect() {
		return dialect;
	}

	/**
	 * <p>端末固有情報を取得します.</p>
	 * <p>
	 * このメソッドを最初に呼び出した時に、
	 * 端末固有情報をUser-Agentをキーに、プロファイル情報から取得する。
	 * </p>
	 * <p>
	 * 端末情報がプロファイル情報に存在しない場合、
	 * このメソッドはnullを返却します。
	 * </p>
	 *
	 * @return	アクセスした端末の端末固有情報
	 */
	@Override
	public Device getDevice() {
		if (!isPopedDevice) {
			device = SingletonUtils.get(DevicePool.class).get();
			if (device != null) {
				RequestUtils.getMobyletContext().set(device);
			}
			isPopedDevice = true;
		}
		return device;
	}

	/**
	 * <p>クッキー保持フラグを取得します.</p>
	 * <p>
	 * このメソッドは、リクエストにクッキー情報が入っている場合にtrueを返却します。<br />
	 * このため、初回アクセスやクッキー（Sessionなど）を利用しないアプリケーションでは、
	 * 必ずfalseが返却されます。
	 * </p>
	 * <p>
	 * 実際にアクセスした端末がクッキー使用可能/不可能の情報を取得したい場合、
	 * 端末固有情報から判定して下さい。
	 * </p>
	 *
	 * @return	このリクエストにクッキーが存在する場合はtrue、しない場合はfalse
	 */
	@Override
	public boolean hasCookies() {
		return RequestUtils.get().getCookies() != null;
	}

	/**
	 * <p>uid情報を取得します.</p>
	 * <p>
	 * docomoの場合は公式サイトでなければ取得することが出来ません。<br />
	 * それぞれのキャリアでの取得するパラメータは以下の通りです。
	 * <ul>
	 * <li>docomo: リクエストパラメータuidの値</li>
	 * <li>au: リクエストヘッダx-subup-noの値</li>
	 * <li>SoftBank: リクエストヘッダx-jphone-uidの値</li>
	 * <li>other: null</li>
	 * </ul>
	 * </p>
	 *
	 * @return	uid
	 */
	@Override
	public String getUid() {
		return dialect.getUid();
	}

	/**
	 * <p>guid情報を取得します.</p>
	 * <p>
	 * docomoの場合にはiモードID、その他のキャリアはuidと同一の値を返却します<br />
	 * それぞれのキャリアでの取得するパラメータは以下の通りです。
	 * <ul>
	 * <li>docomo: リクエストヘッダX-DCMGUIDの値（無ければUIDパラメータ）</li>
	 * <li>その他: <code>getUid()</code>と同じ値</li>
	 * </ul>
	 * </p>
	 *
	 * @return	uid
	 */
	@Override
	public String getGuid() {
		return dialect.getGuid();
	}

	/**
	 * <p>位置情報を取得します.</p>
	 * <p>
	 * 緯度経度情報をパラメータとして受けたリクエスト内でのみ使用可能。<br />
	 * 各キャリアの位置情報をパースして、
	 * <code>Gps</code>インスタンスで返却します。
	 * </p>
	 *
	 * @return	位置情報
	 */
	@Override
	public Gps getGps() {
		MobyletContext context = RequestUtils.getMobyletContext();
		Gps g = context.get(Gps.class);
		if (isPopedGps) {
			return g;
		} else {
			g = dialect.getGps();
			context.set(g);
			isPopedGps = true;
			return getGps();
		}
	}

	/**
	 * <p>ブラウザ画面サイズ情報を返します.</p>
	 * <p>
	 * 端末プロファイル情報に該当端末が存在すればその値を返し、
	 * 無ければHTTPヘッダからディスプレイ情報を取得します。<br />
	 * それでも取得出来ない場合はnullを返却します。
	 * </p>
	 *
	 * @return	ブラウザ画面サイズ情報
	 */
	@Override
	public DeviceDisplay getDisplay() {
		Device device = null;
		DeviceDisplay dp = null;
		if ((device = getDevice()) != null) {
			dp = device.getDeviceDisplay();
		}
		if (dp == null) {
			dp = dialect.getDeviceDisplayByRequestHeader();
		}
		return dp;
	}

	/**
	 * <p>ContentTypeを設定する.</p>
	 * <p>
	 * レスポンスのビューのContentTypeを設定する
	 * </p>
	 *
	 * @param contentType	Content-Type
	 */
	@Override
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	@Override
	public ContentType getContentType() {
		if (contentType == null) {
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			contentType = config.getContentType();
		}
		return contentType;
	}

	/**
	 * <p>初期化処理.</p>
	 * <p>
	 * キャリア情報とダイアレクトの確定処理を行います。
	 * </p>
	 */
	protected void initialize() {
		carrier = SingletonUtils.get(CarrierDetector.class).getCarrier();
		dialect = SingletonUtils.get(DialectSelector.class).getDialect(carrier);
	}
}
