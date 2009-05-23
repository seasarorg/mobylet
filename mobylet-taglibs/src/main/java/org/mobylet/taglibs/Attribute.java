package org.mobylet.taglibs;

public class Attribute {

	public String key;

	public Object value;

	public Attribute() {
		key = null;
		value = null;
	}

	public Attribute(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public static class EmptyValue {

		public EmptyValue() {
			//NOP
		}
	}
}
