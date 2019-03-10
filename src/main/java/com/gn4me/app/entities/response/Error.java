package com.gn4me.app.entities.response;


import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Error {

	private String traceCode;
    private int status;
    private String message;
    
    public Error(ResponseCode responseCode, Transition transition) {
    	
    	if(transition != null)
    		this.traceCode = Long.toString(transition.getId());
    	
    	if(responseCode != null && responseCode != ResponseCode.SUCCESS) {
    		this.status = responseCode.getCode();
    		this.message = responseCode.getMessage();
    	}
    	
    }
    
    
	
}
