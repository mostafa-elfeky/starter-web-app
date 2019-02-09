package com.gn4me.app.log;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.response.ResponseStatus;
import com.gn4me.app.util.AppException;

@Component
public class LogHelper {

	private Logger logger = Logger.getLogger("AppDebugLogger");
	private Logger errorLogger = Logger.getLogger("AppErrorLogger");
	
	public void logThrownExp(Exception exp, String operation, Transition transition) throws AppException {
		exp.printStackTrace();
		logger.error("[" + transition.getId() + "] Exception happened while " + operation + exp);
		errorLogger.error("[" + transition.getId() + "] Exception happened  while " + operation + exp, exp);
		
		throw new AppException(new ResponseStatus(ResponseCode.GENERAL_FAILURE,
				transition.getId()), exp, transition);
	}
	
	public void logExp(Exception exp, String operation, Transition transition) {
		exp.printStackTrace();
		logger.error("[" + transition.getId() + "] Exception happened while " + operation + exp);
		errorLogger.error("[" + transition.getId() + "] Exception happened  while " + operation + exp, exp);
	}
	
	public void log(String operation, Transition transition) {
		logger.error("[" + transition.getId() + "] " + operation );
	}
	
}
