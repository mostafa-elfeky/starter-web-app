package com.gn4me.app.file.enums;

public enum SizeCategory {

	XS("xs"),
	S("s"),
	M("m"),
	L("l"),
	XL("xl"),
	COVER("cover");
	
	private String value;
	
	private SizeCategory(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
