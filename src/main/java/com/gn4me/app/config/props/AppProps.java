package com.gn4me.app.config.props;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProps {
	
	
	private String name;
	private String email;
	private String webUrl;
	private String apiUrl;
	private String logo;
	private String defaultLanguage;
	private String termsAndCondLink;
	private String userResetPasswordUrl;
	

}
