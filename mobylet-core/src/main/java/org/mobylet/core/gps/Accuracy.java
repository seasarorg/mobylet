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
package org.mobylet.core.gps;

public enum Accuracy {

	G300,

	G50L300,

	L50;

	public static Accuracy getAccuracy(int code) {
		switch (code) {
		case 1:
			return G300;
		case 2:
			return G50L300;
		case 3:
			return L50;
		default:
			return null;
		}
	}

}
