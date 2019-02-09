package com.gn4me.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.response.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void commence(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenticationException e)
			throws IOException, ServletException {
		
		ResponseStatus status = new ResponseStatus(ResponseCode.UNAUTHORIZED);

		try {
		
			String response = mapper.writeValueAsString(status);
			
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			
			httpResponse.getWriter().write(response);
		
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	
	
}
