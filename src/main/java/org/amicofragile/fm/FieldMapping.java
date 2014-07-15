package org.amicofragile.fm;

public class FieldMapping {
	private final String fieldIn, fieldOut;

	public FieldMapping(String fieldIn, String fieldOut) {
		this.fieldIn = fieldIn;
		this.fieldOut = fieldOut;
	}

	public String getFieldIn() {
		return fieldIn;
	}

	public String getFieldOut() {
		return fieldOut;
	}
}
