package com.gn4me.app.entities.enums;

public enum Security {
	
	BEARER_PREFIX("Bearer "), 
	SEC_HEADER_PARAM("Authorization"),
	RIGHT_CLAIM("rights"),
	USER_ID_CLAIM("userId"), 
	REFRESH_CLAIM("refresh"),
	RE_LOGIN_STATE("state");
	
	private String value;

	private Security(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
