package com.gn4me.app.log;

public enum Type {
	
	DAO("DAO"),
	SERVICCE("SERVICCE"),
	CONTROLLER("CONTROLLER"),
	UTIL("UTIL");
	
	private String value;

	private Type(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
