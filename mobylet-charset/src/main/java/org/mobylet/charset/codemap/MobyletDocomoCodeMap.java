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
package org.mobylet.charset.codemap;

public class MobyletDocomoCodeMap {

	private static int[] b2c;

	private static int[] c2b;


	static {
		//Char to Byte
		c2b = new int[0xE757 - 0xE63E + 1];
		c2b[0xE63E - 0xE63E] = 0xF89F;
		c2b[0xE63F - 0xE63E] = 0xF8A0;
		c2b[0xE640 - 0xE63E] = 0xF8A1;
		c2b[0xE641 - 0xE63E] = 0xF8A2;
		c2b[0xE642 - 0xE63E] = 0xF8A3;
		c2b[0xE643 - 0xE63E] = 0xF8A4;
		c2b[0xE644 - 0xE63E] = 0xF8A5;
		c2b[0xE645 - 0xE63E] = 0xF8A6;
		c2b[0xE646 - 0xE63E] = 0xF8A7;
		c2b[0xE647 - 0xE63E] = 0xF8A8;
		c2b[0xE648 - 0xE63E] = 0xF8A9;
		c2b[0xE649 - 0xE63E] = 0xF8AA;
		c2b[0xE64A - 0xE63E] = 0xF8AB;
		c2b[0xE64B - 0xE63E] = 0xF8AC;
		c2b[0xE64C - 0xE63E] = 0xF8AD;
		c2b[0xE64D - 0xE63E] = 0xF8AE;
		c2b[0xE64E - 0xE63E] = 0xF8AF;
		c2b[0xE64F - 0xE63E] = 0xF8B0;
		c2b[0xE650 - 0xE63E] = 0xF8B1;
		c2b[0xE651 - 0xE63E] = 0xF8B2;
		c2b[0xE652 - 0xE63E] = 0xF8B3;
		c2b[0xE653 - 0xE63E] = 0xF8B4;
		c2b[0xE654 - 0xE63E] = 0xF8B5;
		c2b[0xE655 - 0xE63E] = 0xF8B6;
		c2b[0xE656 - 0xE63E] = 0xF8B7;
		c2b[0xE657 - 0xE63E] = 0xF8B8;
		c2b[0xE658 - 0xE63E] = 0xF8B9;
		c2b[0xE659 - 0xE63E] = 0xF8BA;
		c2b[0xE65A - 0xE63E] = 0xF8BB;
		c2b[0xE65B - 0xE63E] = 0xF8BC;
		c2b[0xE65C - 0xE63E] = 0xF8BD;
		c2b[0xE65D - 0xE63E] = 0xF8BE;
		c2b[0xE65E - 0xE63E] = 0xF8BF;
		c2b[0xE65F - 0xE63E] = 0xF8C0;
		c2b[0xE660 - 0xE63E] = 0xF8C1;
		c2b[0xE661 - 0xE63E] = 0xF8C2;
		c2b[0xE662 - 0xE63E] = 0xF8C3;
		c2b[0xE663 - 0xE63E] = 0xF8C4;
		c2b[0xE664 - 0xE63E] = 0xF8C5;
		c2b[0xE665 - 0xE63E] = 0xF8C6;
		c2b[0xE666 - 0xE63E] = 0xF8C7;
		c2b[0xE667 - 0xE63E] = 0xF8C8;
		c2b[0xE668 - 0xE63E] = 0xF8C9;
		c2b[0xE669 - 0xE63E] = 0xF8CA;
		c2b[0xE66A - 0xE63E] = 0xF8CB;
		c2b[0xE66B - 0xE63E] = 0xF8CC;
		c2b[0xE66C - 0xE63E] = 0xF8CD;
		c2b[0xE66D - 0xE63E] = 0xF8CE;
		c2b[0xE66E - 0xE63E] = 0xF8CF;
		c2b[0xE66F - 0xE63E] = 0xF8D0;
		c2b[0xE670 - 0xE63E] = 0xF8D1;
		c2b[0xE671 - 0xE63E] = 0xF8D2;
		c2b[0xE672 - 0xE63E] = 0xF8D3;
		c2b[0xE673 - 0xE63E] = 0xF8D4;
		c2b[0xE674 - 0xE63E] = 0xF8D5;
		c2b[0xE675 - 0xE63E] = 0xF8D6;
		c2b[0xE676 - 0xE63E] = 0xF8D7;
		c2b[0xE677 - 0xE63E] = 0xF8D8;
		c2b[0xE678 - 0xE63E] = 0xF8D9;
		c2b[0xE679 - 0xE63E] = 0xF8DA;
		c2b[0xE67A - 0xE63E] = 0xF8DB;
		c2b[0xE67B - 0xE63E] = 0xF8DC;
		c2b[0xE67C - 0xE63E] = 0xF8DD;
		c2b[0xE67D - 0xE63E] = 0xF8DE;
		c2b[0xE67E - 0xE63E] = 0xF8DF;
		c2b[0xE67F - 0xE63E] = 0xF8E0;
		c2b[0xE680 - 0xE63E] = 0xF8E1;
		c2b[0xE681 - 0xE63E] = 0xF8E2;
		c2b[0xE682 - 0xE63E] = 0xF8E3;
		c2b[0xE683 - 0xE63E] = 0xF8E4;
		c2b[0xE684 - 0xE63E] = 0xF8E5;
		c2b[0xE685 - 0xE63E] = 0xF8E6;
		c2b[0xE686 - 0xE63E] = 0xF8E7;
		c2b[0xE687 - 0xE63E] = 0xF8E8;
		c2b[0xE688 - 0xE63E] = 0xF8E9;
		c2b[0xE689 - 0xE63E] = 0xF8EA;
		c2b[0xE68A - 0xE63E] = 0xF8EB;
		c2b[0xE68B - 0xE63E] = 0xF8EC;
		c2b[0xE68C - 0xE63E] = 0xF8ED;
		c2b[0xE68D - 0xE63E] = 0xF8EE;
		c2b[0xE68E - 0xE63E] = 0xF8EF;
		c2b[0xE68F - 0xE63E] = 0xF8F0;
		c2b[0xE690 - 0xE63E] = 0xF8F1;
		c2b[0xE691 - 0xE63E] = 0xF8F2;
		c2b[0xE692 - 0xE63E] = 0xF8F3;
		c2b[0xE693 - 0xE63E] = 0xF8F4;
		c2b[0xE694 - 0xE63E] = 0xF8F5;
		c2b[0xE695 - 0xE63E] = 0xF8F6;
		c2b[0xE696 - 0xE63E] = 0xF8F7;
		c2b[0xE697 - 0xE63E] = 0xF8F8;
		c2b[0xE698 - 0xE63E] = 0xF8F9;
		c2b[0xE699 - 0xE63E] = 0xF8FA;
		c2b[0xE69A - 0xE63E] = 0xF8FB;
		c2b[0xE69B - 0xE63E] = 0xF8FC;
		c2b[0xE69C - 0xE63E] = 0xF940;
		c2b[0xE69D - 0xE63E] = 0xF941;
		c2b[0xE69E - 0xE63E] = 0xF942;
		c2b[0xE69F - 0xE63E] = 0xF943;
		c2b[0xE6A0 - 0xE63E] = 0xF944;
		c2b[0xE6A1 - 0xE63E] = 0xF945;
		c2b[0xE6A2 - 0xE63E] = 0xF946;
		c2b[0xE6A3 - 0xE63E] = 0xF947;
		c2b[0xE6A4 - 0xE63E] = 0xF948;
		c2b[0xE6A5 - 0xE63E] = 0xF949;
		c2b[0xE6CE - 0xE63E] = 0xF972;
		c2b[0xE6CF - 0xE63E] = 0xF973;
		c2b[0xE6D0 - 0xE63E] = 0xF974;
		c2b[0xE6D1 - 0xE63E] = 0xF975;
		c2b[0xE6D2 - 0xE63E] = 0xF976;
		c2b[0xE6D3 - 0xE63E] = 0xF977;
		c2b[0xE6D4 - 0xE63E] = 0xF978;
		c2b[0xE6D5 - 0xE63E] = 0xF979;
		c2b[0xE6D6 - 0xE63E] = 0xF97A;
		c2b[0xE6D7 - 0xE63E] = 0xF97B;
		c2b[0xE6D8 - 0xE63E] = 0xF97C;
		c2b[0xE6D9 - 0xE63E] = 0xF97D;
		c2b[0xE6DA - 0xE63E] = 0xF97E;
		c2b[0xE6DB - 0xE63E] = 0xF980;
		c2b[0xE6DC - 0xE63E] = 0xF981;
		c2b[0xE6DD - 0xE63E] = 0xF982;
		c2b[0xE6DE - 0xE63E] = 0xF983;
		c2b[0xE6DF - 0xE63E] = 0xF984;
		c2b[0xE6E0 - 0xE63E] = 0xF985;
		c2b[0xE6E1 - 0xE63E] = 0xF986;
		c2b[0xE6E2 - 0xE63E] = 0xF987;
		c2b[0xE6E3 - 0xE63E] = 0xF988;
		c2b[0xE6E4 - 0xE63E] = 0xF989;
		c2b[0xE6E5 - 0xE63E] = 0xF98A;
		c2b[0xE6E6 - 0xE63E] = 0xF98B;
		c2b[0xE6E7 - 0xE63E] = 0xF98C;
		c2b[0xE6E8 - 0xE63E] = 0xF98D;
		c2b[0xE6E9 - 0xE63E] = 0xF98E;
		c2b[0xE6EA - 0xE63E] = 0xF98F;
		c2b[0xE6EB - 0xE63E] = 0xF990;
		c2b[0xE70B - 0xE63E] = 0xF9B0;
		c2b[0xE6EC - 0xE63E] = 0xF991;
		c2b[0xE6ED - 0xE63E] = 0xF992;
		c2b[0xE6EE - 0xE63E] = 0xF993;
		c2b[0xE6EF - 0xE63E] = 0xF994;
		c2b[0xE6F0 - 0xE63E] = 0xF995;
		c2b[0xE6F1 - 0xE63E] = 0xF996;
		c2b[0xE6F2 - 0xE63E] = 0xF997;
		c2b[0xE6F3 - 0xE63E] = 0xF998;
		c2b[0xE6F4 - 0xE63E] = 0xF999;
		c2b[0xE6F5 - 0xE63E] = 0xF99A;
		c2b[0xE6F6 - 0xE63E] = 0xF99B;
		c2b[0xE6F7 - 0xE63E] = 0xF99C;
		c2b[0xE6F8 - 0xE63E] = 0xF99D;
		c2b[0xE6F9 - 0xE63E] = 0xF99E;
		c2b[0xE6FA - 0xE63E] = 0xF99F;
		c2b[0xE6FB - 0xE63E] = 0xF9A0;
		c2b[0xE6FC - 0xE63E] = 0xF9A1;
		c2b[0xE6FD - 0xE63E] = 0xF9A2;
		c2b[0xE6FE - 0xE63E] = 0xF9A3;
		c2b[0xE6FF - 0xE63E] = 0xF9A4;
		c2b[0xE700 - 0xE63E] = 0xF9A5;
		c2b[0xE701 - 0xE63E] = 0xF9A6;
		c2b[0xE702 - 0xE63E] = 0xF9A7;
		c2b[0xE703 - 0xE63E] = 0xF9A8;
		c2b[0xE704 - 0xE63E] = 0xF9A9;
		c2b[0xE705 - 0xE63E] = 0xF9AA;
		c2b[0xE706 - 0xE63E] = 0xF9AB;
		c2b[0xE707 - 0xE63E] = 0xF9AC;
		c2b[0xE708 - 0xE63E] = 0xF9AD;
		c2b[0xE709 - 0xE63E] = 0xF9AE;
		c2b[0xE70A - 0xE63E] = 0xF9AF;
		c2b[0xE6AC - 0xE63E] = 0xF950;
		c2b[0xE6AD - 0xE63E] = 0xF951;
		c2b[0xE6AE - 0xE63E] = 0xF952;
		c2b[0xE6B1 - 0xE63E] = 0xF955;
		c2b[0xE6B2 - 0xE63E] = 0xF956;
		c2b[0xE6B3 - 0xE63E] = 0xF957;
		c2b[0xE6B7 - 0xE63E] = 0xF95B;
		c2b[0xE6B8 - 0xE63E] = 0xF95C;
		c2b[0xE6B9 - 0xE63E] = 0xF95D;
		c2b[0xE6BA - 0xE63E] = 0xF95E;
		c2b[0xE70C - 0xE63E] = 0xF9B1;
		c2b[0xE70D - 0xE63E] = 0xF9B2;
		c2b[0xE70E - 0xE63E] = 0xF9B3;
		c2b[0xE70F - 0xE63E] = 0xF9B4;
		c2b[0xE710 - 0xE63E] = 0xF9B5;
		c2b[0xE711 - 0xE63E] = 0xF9B6;
		c2b[0xE712 - 0xE63E] = 0xF9B7;
		c2b[0xE713 - 0xE63E] = 0xF9B8;
		c2b[0xE714 - 0xE63E] = 0xF9B9;
		c2b[0xE715 - 0xE63E] = 0xF9BA;
		c2b[0xE716 - 0xE63E] = 0xF9BB;
		c2b[0xE717 - 0xE63E] = 0xF9BC;
		c2b[0xE718 - 0xE63E] = 0xF9BD;
		c2b[0xE719 - 0xE63E] = 0xF9BE;
		c2b[0xE71A - 0xE63E] = 0xF9BF;
		c2b[0xE71B - 0xE63E] = 0xF9C0;
		c2b[0xE71C - 0xE63E] = 0xF9C1;
		c2b[0xE71D - 0xE63E] = 0xF9C2;
		c2b[0xE71E - 0xE63E] = 0xF9C3;
		c2b[0xE71F - 0xE63E] = 0xF9C4;
		c2b[0xE720 - 0xE63E] = 0xF9C5;
		c2b[0xE721 - 0xE63E] = 0xF9C6;
		c2b[0xE722 - 0xE63E] = 0xF9C7;
		c2b[0xE723 - 0xE63E] = 0xF9C8;
		c2b[0xE724 - 0xE63E] = 0xF9C9;
		c2b[0xE725 - 0xE63E] = 0xF9CA;
		c2b[0xE726 - 0xE63E] = 0xF9CB;
		c2b[0xE727 - 0xE63E] = 0xF9CC;
		c2b[0xE728 - 0xE63E] = 0xF9CD;
		c2b[0xE729 - 0xE63E] = 0xF9CE;
		c2b[0xE72A - 0xE63E] = 0xF9CF;
		c2b[0xE72B - 0xE63E] = 0xF9D0;
		c2b[0xE72C - 0xE63E] = 0xF9D1;
		c2b[0xE72D - 0xE63E] = 0xF9D2;
		c2b[0xE72E - 0xE63E] = 0xF9D3;
		c2b[0xE72F - 0xE63E] = 0xF9D4;
		c2b[0xE730 - 0xE63E] = 0xF9D5;
		c2b[0xE731 - 0xE63E] = 0xF9D6;
		c2b[0xE732 - 0xE63E] = 0xF9D7;
		c2b[0xE733 - 0xE63E] = 0xF9D8;
		c2b[0xE734 - 0xE63E] = 0xF9D9;
		c2b[0xE735 - 0xE63E] = 0xF9DA;
		c2b[0xE736 - 0xE63E] = 0xF9DB;
		c2b[0xE737 - 0xE63E] = 0xF9DC;
		c2b[0xE738 - 0xE63E] = 0xF9DD;
		c2b[0xE739 - 0xE63E] = 0xF9DE;
		c2b[0xE73A - 0xE63E] = 0xF9DF;
		c2b[0xE73B - 0xE63E] = 0xF9E0;
		c2b[0xE73C - 0xE63E] = 0xF9E1;
		c2b[0xE73D - 0xE63E] = 0xF9E2;
		c2b[0xE73E - 0xE63E] = 0xF9E3;
		c2b[0xE73F - 0xE63E] = 0xF9E4;
		c2b[0xE740 - 0xE63E] = 0xF9E5;
		c2b[0xE741 - 0xE63E] = 0xF9E6;
		c2b[0xE742 - 0xE63E] = 0xF9E7;
		c2b[0xE743 - 0xE63E] = 0xF9E8;
		c2b[0xE744 - 0xE63E] = 0xF9E9;
		c2b[0xE745 - 0xE63E] = 0xF9EA;
		c2b[0xE746 - 0xE63E] = 0xF9EB;
		c2b[0xE747 - 0xE63E] = 0xF9EC;
		c2b[0xE748 - 0xE63E] = 0xF9ED;
		c2b[0xE749 - 0xE63E] = 0xF9EE;
		c2b[0xE74A - 0xE63E] = 0xF9EF;
		c2b[0xE74B - 0xE63E] = 0xF9F0;
		c2b[0xE74C - 0xE63E] = 0xF9F1;
		c2b[0xE74D - 0xE63E] = 0xF9F2;
		c2b[0xE74E - 0xE63E] = 0xF9F3;
		c2b[0xE74F - 0xE63E] = 0xF9F4;
		c2b[0xE750 - 0xE63E] = 0xF9F5;
		c2b[0xE751 - 0xE63E] = 0xF9F6;
		c2b[0xE752 - 0xE63E] = 0xF9F7;
		c2b[0xE753 - 0xE63E] = 0xF9F8;
		c2b[0xE754 - 0xE63E] = 0xF9F9;
		c2b[0xE755 - 0xE63E] = 0xF9FA;
		c2b[0xE756 - 0xE63E] = 0xF9FB;
		c2b[0xE757 - 0xE63E] = 0xF9FC;
		for (int i=0; i<c2b.length; i++) {
			if (c2b[i] == 0) {
				c2b[i] = 0x3F3F;
			}
		}

		//Byte to Char
		b2c = new int[0xF9FC - 0xF89F + 1];
		for (int i=0; i<c2b.length; i++) {
			int b = c2b[i];
			if (b != 0x3F3F) {
				b2c[b - 0xF89F] = i + 0xE63E;
			}
		}
		for (int i=0; i<b2c.length; i++) {
			if (b2c[i] == 0) {
				b2c[i] = 0x3F;
			}
		}
	}

	public static int toByte(int c) {
		return c2b[c - 0xE63E];
	}

	public static char toChar(int b) {
		return (char)b2c[b - 0xF89F];
	}
}
