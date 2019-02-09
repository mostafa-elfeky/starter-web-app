package com.gn4me.app.entities.enums;

public enum Language {
	
	EN("en"), AR("ar");
	
	private String value;

	private Language(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
