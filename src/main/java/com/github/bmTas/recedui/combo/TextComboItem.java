package com.github.bmTas.recedui.combo;

public class TextComboItem implements ITextComboItem {

	private final String value;
	private final String displayValue;
	

	public TextComboItem(String value, String displayValue) {
		super();
		this.value = value;
		this.displayValue = displayValue;
	}
	

	@Override
	public String getCode() {
		return value;
	}

	@Override
	public String toString() {
		return displayValue;
	}
}
