package com.gn4me.app.config;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.gn4me.app.config.props.FileProps;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.file.FileUploader;
import com.gn4me.app.log.LogHelper;
import com.gn4me.app.util.SystemFileUploader;
import com.gn4me.app.util.UtilHandler;


@Configuration
public class WebConfig {
	
	@Autowired
	private UtilHandler utilHandler;
	
	@Autowired
	LogHelper logHelper;
	
	@Autowired
	private FileProps fileProps;

   

    // Load common parameters of controllers in all the system 
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//      argumentResolvers.add(new TransitionArgumentResolver(utilHandler));
//    }
    
    @Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("mail01.gn4me.com");
	    mailSender.setPort(25);
	     
	    mailSender.setUsername("exchange@gn4me.com");
	    mailSender.setPassword("Gnns_1");
	    mailSender.setDefaultEncoding("UTF-8");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	
	@Bean
	public FileUploader getFileUploader() {
		FileUploader fileUploader = null;
		switch (fileProps.getUploader()) {
		case SYSTEM:
			fileUploader = new SystemFileUploader(logHelper, fileProps);
			break;
		case AMAZON:
			logHelper.log(">>>> There is no Amazon File Uploader Implementaion Yet...", new Transition());
			break;
		case AZURE:
			logHelper.log(">>>> There is no Microsoft Azure File Uploader Implementaion Yet...", new Transition());
			break;
		}
	    return fileUploader;
	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(fileProps.getFileMaxSize() * 1024);
	    return multipartResolver;
	}
}
