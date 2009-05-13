package org.mobylet.core.factory;

import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.detector.impl.MobyletCarrierDetector;

public class CarrierDetectorFactory {

	public static CarrierDetector getCarrierDetector() {
		//TODO 設定ファイルから読み込むようにする
		return new MobyletCarrierDetector();
	}
}
