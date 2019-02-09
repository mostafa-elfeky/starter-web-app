package com.gn4me.app.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gn4me.app.config.security.JwtAuthenticationEntryPoint;
import com.gn4me.app.config.security.JwtTokenFilter;
import com.gn4me.app.config.security.JwtTokenProvider;
import com.gn4me.app.util.UtilHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  
  @Autowired
  private JwtAuthenticationEntryPoint entryPoint;
  
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private ObjectMapper mapper;
  
  @Autowired
  private UtilHandler utilHandler;
  
  @Bean
  public JwtTokenFilter getJwtTokenFilter() {
	  return new JwtTokenFilter(jwtTokenProvider, mapper, utilHandler);
  }
  
  //configure spring security and JWT filter
  
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
	  
	  httpSecurity
		  .csrf().disable()
			
	      .exceptionHandling().authenticationEntryPoint(entryPoint).and()
	
	      // don't create session
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		  .antMatcher("/api/**")
		  .authorizeRequests() 
	      .anyRequest().authenticated()
	      .and()
		  .addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	  httpSecurity.headers().cacheControl();
   
  }

  // configure static resources to avoid applying security on them
  @Override
  public void configure(WebSecurity webSecurity) throws Exception {
	 
	// Allow swagger to be accessed without authentication
	webSecurity
    	.ignoring()
    	.antMatchers("/v2/api-docs")//
        .antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/public");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
  
  
}
