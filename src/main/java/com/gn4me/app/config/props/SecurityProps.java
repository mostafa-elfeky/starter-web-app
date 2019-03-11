package com.gn4me.app.config.props;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;



import lombok.Data;


@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProps {
	
	private String secretKey;
	private long maxRefreshRate;
	private int reLoginState;
	private int expiredWithin;
	
}
