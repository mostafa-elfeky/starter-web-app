package com.gn4me.app.file.enums;

public enum Uploader {

	SYSTEM("System"),
	AMAZON("Amazon"),
	AZURE("Azure");

	private String value;
	
	private Uploader(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
