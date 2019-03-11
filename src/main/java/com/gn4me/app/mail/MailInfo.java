package com.gn4me.app.mail;

import com.gn4me.app.config.props.AppProps;
import com.gn4me.app.entities.User;

import lombok.Data;

@Data
public class MailInfo {

	private String sender;
	private User receiver;
	private AppProps appInfo;
	private String subject;
	private String message;
	
	
}
