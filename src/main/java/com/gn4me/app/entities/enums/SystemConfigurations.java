package com.gn4me.app.entities.enums;

public enum SystemConfigurations {
	
	CATEGORY_ICON_DEFAULT("CATEGORY_ICON_DEFAULT");
	
	private String value;
	
	private SystemConfigurations(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
