package com.gn4me.app.entities.response;


import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ErrorCode;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Error {

	private String traceCode;
    private int status;
    private String message;
    
    public Error(ErrorCode errorCode, Transition transition) {
    	
    	if(transition != null)
    		this.traceCode = Long.toString(transition.getId());
    	
    	if(errorCode != null) {
    		this.status = errorCode.getCode();
    		this.message = errorCode.getMessage();
    	}
    	
    }
    
    
	
}
