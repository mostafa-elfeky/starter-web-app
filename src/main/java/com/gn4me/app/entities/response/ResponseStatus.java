package com.gn4me.app.entities.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStatus {

	private int code;
	private String message;
	private Long traceCode;
	
	
	public ResponseStatus(){}
	
	public ResponseStatus(ResponseCode response) {
		this.code = response.getCode();
		this.message = response.getMessage();
	}

	public ResponseStatus(ResponseCode response, Transition transition) {
		this.code = response.getCode();
		this.message = response.getMessage();
		this.traceCode = transition.getId();
	}
	
	public ResponseStatus(ResponseCode response, String description) {
		this.code = response.getCode();
		this.message = response.getMessage()  + ", " + description;
	}
	
	public ResponseStatus(ResponseCode response, String description, Transition transition) {
		this.code = response.getCode();
		this.message = response.getMessage()  + ", " + description;
		this.traceCode = transition.getId();
	}
	
	public ResponseStatus(ResponseCode response, long traceCode) {
		this.code = response.getCode();
		this.message = response.getMessage();
		this.traceCode = traceCode;
	}
	
}
