package com.gn4me.app.config.props;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.gn4me.app.entities.enums.SystemModuleEnum;
import com.gn4me.app.file.enums.ContentType;

import lombok.Getter;
import lombok.ToString;
import lombok.AccessLevel;


@Component
@Getter @ToString(exclude="env")
public class AppInfo {
	
	@Autowired
	@Getter(AccessLevel.NONE)
	private Environment env;
	
	private String name;
	private String email;
	private String webUrl;
	private String apiUrl;
	private String logo;
	private String defaultLanguage;
	private String termsAndCondLink;
	private String userResetPasswordUrl;
	
	@PostConstruct
	public void refresh() {
		this.name = env.getProperty("app.info.name");
		this.email = env.getProperty("app.info.email");
		this.webUrl = env.getProperty("app.info.web.url");
		this.apiUrl = env.getProperty("app.info.api.url");
		this.logo = env.getProperty("app.info.logo");
		this.defaultLanguage = env.getProperty("app.info.language");
		this.termsAndCondLink = env.getProperty("app.info.terms-and-conditions");
		this.userResetPasswordUrl = env.getProperty("app.info.user.rest-password.url");
	}

}
