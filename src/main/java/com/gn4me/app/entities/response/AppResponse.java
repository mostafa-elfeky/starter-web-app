package com.gn4me.app.entities.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse<D> {
	
	private D data;
	private Error error;
	private Map<String, String> metaInfo = new HashMap<>();
	
	@JsonIgnore
	private HttpStatus httpStatus;
	
	public AppResponse(Map<String, String> metaInfo, D data, Error error, HttpStatus httpStatus,  Transition transition) {
		this.data = data;
		this.metaInfo = metaInfo;
		this.httpStatus = httpStatus;
		this.error = error;
	}
	
	
	public static<D> ResponseBuilder<D> builder(Transition transition) {
		return new ResponseBuilder<>(transition);
	}
	
	public static class ResponseBuilder<D> {
		
		private Map<String, String> info = new HashMap<>();
		private ResponseCode responseCode = ResponseCode.SUCCESS;
		
		private D data;
		private Transition transition;
		
		public ResponseBuilder(Transition transition) {
			this.transition = transition;
		}
		
		public ResponseBuilder<D> info(String key, String value) {
			if(key != null && value != null)
				info.put(key, value);
			return this;
		}
		
		public ResponseBuilder<D> data(D data) {
			if(data != null) 
				this.data = data;
			else 
				this.responseCode = ResponseCode.NOT_FOUND;
			return this;
		}
		
		public ResponseBuilder<D> status(ResponseCode responseCode) {
			if(responseCode != null && responseCode != ResponseCode.SUCCESS)
				this.responseCode = responseCode;
			return this;
		}
		
		public AppResponse<D> build() {
			
			Error error = null;
			
			if(responseCode.getHttpStatus() != HttpStatus.OK)
				error = new Error(responseCode, transition);
			
            return new AppResponse<D>(info, data, error, responseCode.getHttpStatus(), transition);
        }
		
    }
	
	
	
}
