package com.gn4me.app.entities.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class AppResponse<D> {
	
	private D data;
	private Error error;
	
	private Map<String, String> metaInfo = new HashMap<>();
	
}
