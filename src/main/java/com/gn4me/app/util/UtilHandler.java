package com.gn4me.app.util;


import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.gn4me.app.config.props.AppProps;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.Language;
import com.gn4me.app.log.LogHelper;

import lombok.Data;


@Component
@Data
public class UtilHandler {

	@Autowired
	LogHelper logHelper;
	
	@Autowired
	AppProps appInfo;
	
	public Language validateLanguage(String lang) throws Exception {
		if(lang != null && lang != "") {
			return getLanguage(lang);
		} else if(appInfo.getDefaultLanguage() != null ) {
			return getLanguage(appInfo.getDefaultLanguage());
		} else {
			throw new Exception("Invalid Supplied Language");
		}
		
	}
	
	public Language getLanguage(String lang) {
		if(lang.equalsIgnoreCase(Language.AR.getValue())) {
			return Language.AR;
		} else {
			return Language.EN;
		}
	}
	
	public Date getDateWith(Date date, int milliSecond) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.MILLISECOND, milliSecond);
	    return calendar.getTime();
	}
	
}
