package org.mobylet.view.designer;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;

public abstract class SingletonDesigner {

	public static <D extends SingletonDesigner> D getDesigner(Class<D> clazz) {
		D designer = null;
		try {
			designer = SingletonUtils.get(clazz);
		} catch (Exception e) {
			//NOP
		}
		if (designer == null) {
			try {
				designer = clazz.newInstance();
			} catch (Exception e) {
				throw new MobyletRuntimeException(
						"Designerの初期化に失敗 designer = " + clazz.getName(), e);
			}
			SingletonUtils.put(designer);
			return getDesigner(clazz);
		}
		return designer;
	}

}
