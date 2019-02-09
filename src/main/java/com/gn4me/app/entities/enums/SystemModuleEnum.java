package com.gn4me.app.entities.enums;


public enum SystemModuleEnum {

	ALL("All","Module"),
	USER("User" ,"User");
	
	private String valueEn;
	private String valueAR;
	
	private SystemModuleEnum(String valueEn ,String valueAR) {
		this.valueEn = valueEn;
		this.valueAR = valueAR;
	}

	public String getValue(String lang) {
		if(lang.equals(Language.AR.getValue())) {
			return valueAR;
		} else {
			return valueEn;
		}
	}
}
