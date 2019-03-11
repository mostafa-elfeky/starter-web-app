package com.gn4me.app.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.response.ResponseStatus;
import com.gn4me.app.util.AppException;

@Component
public class LogHelper {

	private final static Logger logger = LoggerFactory.getLogger(LogHelper.class);
	
	public void logThrownExp(Exception exp, String operation, Transition transition) throws AppException {
		logger.error("[" + transition.getId() + "] Exception happened while " + operation + exp);
		logger.error("[" + transition.getId() + "] Exception happened  while " + operation + exp, exp);
		
		throw new AppException(new ResponseStatus(ResponseCode.GENERAL_FAILURE,
				transition.getId()), exp, transition);
	}
	
	public void logExp(Exception exp, String operation, Transition transition) {
		logger.error("[" + transition.getId() + "] Exception happened while " + operation + exp);
		logger.error("[" + transition.getId() + "] Exception happened  while " + operation + exp, exp);
	}
	
	public void log(String operation, Transition transition) {
		logger.debug("[" + transition.getId() + "] " + operation );
	}
	
}
