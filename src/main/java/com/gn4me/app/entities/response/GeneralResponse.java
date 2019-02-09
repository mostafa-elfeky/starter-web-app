package com.gn4me.app.entities.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;

import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class GeneralResponse {

	@JsonProperty("status")
	private ResponseStatus responseStatus;

	
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	public void setResponseStatus(ResponseCode code) {
		this.responseStatus = new ResponseStatus(code);
	}
	
	public void setResponseStatus(ResponseCode code, Transition transition) {
		this.responseStatus = new ResponseStatus(code, transition );
	}

}
