package com.gn4me.app.log;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StopWatch;

import com.gn4me.app.entities.Transition;

@Aspect
//@Component
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class LoggingAspect {

	private Logger logger = Logger.getLogger("AppDebugLogger");
	private Logger errorLogger = Logger.getLogger("AppErrorLogger");

	@Pointcut("within(@Loggable *)")
	public void classAnnotaionLevel() {
	}

	@Pointcut("@annotation(Loggable)")
	public void methodAnnotaionLevel() {
	}

	@Around("classAnnotaionLevel() || methodAnnotaionLevel() ")
	public Object loggingAnnotaion(ProceedingJoinPoint joinPoint) throws Throwable {

		Object retVal = null;

		StringBuilder logEndMessage = null;
		StopWatch stopWatch = null;
		
		try {
			
			stopWatch = new StopWatch();
			stopWatch.start();

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			
			String start = start(joinPoint.getArgs());

			StringBuilder logStartMessage = new StringBuilder(start);
			logEndMessage = new StringBuilder(start);

			// Initialize Log Message
			preLogOperationMessage(logStartMessage, logEndMessage, method);

			// Log Parameters to Start Log Message
			logParameters(logStartMessage, joinPoint);
			
			// test log Start Message
			logger.debug(logStartMessage);
			
		} catch (Exception exp) {
			logger.error("Exception In Logger try to solve It: " + exp);
			errorLogger.error("Exception In Logger try to solve It: " + exp, exp);
			return joinPoint.proceed();
		}
		
		
		// Execute Basic Method
		retVal = joinPoint.proceed();
		
		
		try {
			
			// Log Result to end Message
			logResult(logEndMessage, retVal);

			stopWatch.stop();

			logEndMessage.append(" Exe Time: [ ").append(stopWatch.getTotalTimeMillis()).append(" ms ]");

			// test log End Message
			logger.debug(logEndMessage);
			
		} catch (Exception exp) {
			logger.error("Exception In Logger try to solve It: " + exp);
			errorLogger.error("Exception In Logger try to solve It: " + exp, exp);
		}

		return retVal;
	}

	public void preLogOperationMessage(StringBuilder logStartMessage, StringBuilder logEndMessage, Method method)
			throws Exception {

		Loggable logging = null;
		StringBuilder operation = getOprationDesc(method.getName());

		// Going to get Annotation Value
		if (method.getAnnotation(Loggable.class) != null) {
			logging = method.getAnnotation(Loggable.class);
		} else {
			Class<?> declaringClass = method.getDeclaringClass();
			logging = declaringClass.getAnnotation(Loggable.class);
		}

		// build Start Logging Message
		if (logging != null) {
			switch (logging.Type()) {
			case CONTROLLER:
				logStartMessage.append(" ***** Recieved ").append(operation).append(" Request ");
				logEndMessage.append(" >>>>> Response Sent To User Of ").append(operation).append(" Is ");
				break;
			default:
				logStartMessage.append(" Going to ").append(operation);
				logEndMessage.append(" Result Of ").append(operation);
			}
		}

	}

	public StringBuilder logParameters(StringBuilder builder, ProceedingJoinPoint joinPoint) throws Exception {

		CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
		int length = joinPoint.getArgs().length;
		String prefix = ", ";

		builder.append(" with Parammters {{ ");
		for (int i = 0; i < length; i++) {
			boolean isTransition = joinPoint.getArgs()[i] instanceof Transition;
			
			if(!isTransition) {
				if(codeSignature.getParameterNames() != null) {
					builder
					 .append(codeSignature.getParameterNames()[i]).append(": ")
					 .append(joinPoint.getArgs()[i]).append(prefix);
				} else {
					builder.append(joinPoint.getArgs()[i]).append(prefix);
				}
			}
		}
		builder.append(" }}");

		return builder;
	}

	public void logResult(StringBuilder logEndMessage, Object retVal) throws Exception {
		if (retVal != null) {
			logEndMessage.append(" {{ ").append(retVal.getClass().getSimpleName()).append(" : ").append(retVal)
					.append(" }}");
		} else {
			logEndMessage.append(" {{ void }}");
		}
	}

	private StringBuilder getOprationDesc(String methodName) throws Exception {

		StringBuilder builder = new StringBuilder();

		for (String word : methodName.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
			builder.append(word).append(" ");
		}
		return builder;
	}

	public String start(Object[] args) throws Exception {

		StringBuilder builder = new StringBuilder("[");
		int argLength = args.length;

		if (argLength > 0) {
			Object object = args[argLength - 1];
			if (object instanceof Transition) {
				Transition transition = (Transition) object;
				builder.append(transition.getId()).append("] ");
			}
		}

		return builder.toString();
	}

}
