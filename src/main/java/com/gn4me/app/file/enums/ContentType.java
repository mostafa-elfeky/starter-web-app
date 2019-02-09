package com.gn4me.app.file.enums;

public enum ContentType {

	FILE("File"),
	IMAGE("Image"),
	VIDEO("Video");

	private String value;
	
	private ContentType(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
