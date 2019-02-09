package com.gn4me.app.entities;

import lombok.Data;

@Data
public class SystemConfiguration {
	
	private int id;
	
	private String key;
	
	private String value;
	
	private boolean frontEndDisplay;
}
