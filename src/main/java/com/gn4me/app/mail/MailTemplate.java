package com.gn4me.app.mail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MailTemplate {

	private static Mustache basicTemplate;
	
	private MailTemplate() {}
	
	private static Mustache buildBasicTemplate() {
		if(MailTemplate.basicTemplate == null) {
			MustacheFactory mf = new DefaultMustacheFactory();
			MailTemplate.basicTemplate = mf.compile("basicMailTemplate.mustache");
		}
		return MailTemplate.basicTemplate;
	}
	
	public static MailInfo getBasicTemplate(BasicMailInfo info) throws IOException {
				
		HashMap<String, BasicMailInfo> basicInfoWrapper = new HashMap<String, BasicMailInfo>();
		basicInfoWrapper.put("info", info);
	    
		StringWriter writer = new StringWriter();
	    MailTemplate.buildBasicTemplate().execute(writer, basicInfoWrapper);
	    writer.flush();
	    info.setMessage(writer.toString());
		return info;
	}
	
}
