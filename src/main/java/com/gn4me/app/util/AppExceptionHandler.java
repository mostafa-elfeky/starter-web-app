package com.gn4me.app.util;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.response.GeneralResponse;
import com.gn4me.app.entities.response.ResponseStatus;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppExceptionHandler {

	private final static Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

	@ExceptionHandler(AppException.class)
	@ResponseBody
	public GeneralResponse handleGeneralException(AppException exp) {
		
		ResponseStatus status = null;
		GeneralResponse response = new GeneralResponse();
		Transition transition = exp.getTransition();

		logger.error("[" + transition.getId() + "] Exception happened From, " + exp.getLocalizedMessage(), exp);

		if(exp.getStatus() != null) {
			status = exp.getStatus();
			status.setTraceCode(exp.getTransition().getId());
		} else {
			status = new ResponseStatus(ResponseCode.GENERAL_FAILURE, exp.getTransition());
		}

		response.setResponseStatus(status);
		return response;
	}

	@ExceptionHandler({
		MissingServletRequestParameterException.class,
		IllegalArgumentException.class,
		HttpRequestMethodNotSupportedException.class
	})
	@ResponseBody
	public GeneralResponse handleUnrecognizedPropertyException(Exception exp) {
		
		Transition transition = new Transition();
		exp.printStackTrace();
		
		logger.error("[" + transition.getId() + "] Exception happened From, " +exp.getCause() + exp, exp);
		
		ResponseStatus responseStatus = new ResponseStatus(ResponseCode.BAD_REQUEST, transition);
		GeneralResponse generalResponse = new GeneralResponse();

		generalResponse.setResponseStatus(responseStatus);

		return generalResponse;
	}
	
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseBody
    public GeneralResponse handleNotFoundException(final Exception exp) {
		
		Transition transition = new Transition();
		
		logger.error("[" + transition.getId() + "] Exception happened From, " +exp.getCause() + exp, exp);
		
		ResponseStatus responseStatus = 
				new ResponseStatus(ResponseCode.NOT_FOUND, transition);
		GeneralResponse generalResponse = new GeneralResponse();

		generalResponse.setResponseStatus(responseStatus);

		return generalResponse;
    }
 
	@ExceptionHandler({AccessDeniedException.class,
		AuthenticationCredentialsNotFoundException.class})
	@ResponseBody
    public GeneralResponse handleForbiddenException(Exception exp) {
		
		Transition transition = new Transition();
		//exp.printStackTrace();
		logger.error("[" + transition.getId() + "] Exception happened From, " +exp.getCause() + exp, exp);
		
		ResponseStatus responseStatus = 
				new ResponseStatus(ResponseCode.FORBIDDEN, transition);
		GeneralResponse generalResponse = new GeneralResponse();

		generalResponse.setResponseStatus(responseStatus);

		return generalResponse;
    }    
 
}

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class DefaultExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
    public GeneralResponse handleINTERNAL_SERVER_ERRORException(Exception exp) {
		
		//keep this until we reach a stable face.
		System.out.println("AppExceptionHandler, " + exp);
		exp.printStackTrace();
		
		Transition transition = new Transition();
		
		logger.error("[" + transition.getId() + "] Exception happened From, " +exp.getCause() + exp, exp);
		
		ResponseStatus responseStatus = 
				new ResponseStatus(ResponseCode.INTERNAL_SERVER_ERROR, transition);
		GeneralResponse generalResponse = new GeneralResponse();

		generalResponse.setResponseStatus(responseStatus);

		return generalResponse;
    }
	
}