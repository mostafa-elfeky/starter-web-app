package com.gn4me.app.config.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.User;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.response.ResponseStatus;
import com.gn4me.app.util.AppException;
import com.gn4me.app.util.UtilHandler;

public class JwtTokenFilter extends GenericFilterBean {

	private JwtTokenProvider jwtTokenProvider;
	private ObjectMapper mapper;
	private UtilHandler utilHandler;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper mapper, UtilHandler utilHandler) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.mapper = mapper;
		this.utilHandler = utilHandler;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		try {

			Transition transition = null;
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String token = jwtTokenProvider.resolveToken(httpServletRequest);

			if (token != null) {
				User user = jwtTokenProvider.getUserAuth(token, transition);
				Authentication authentication = jwtTokenProvider.getAuthentication(user, transition);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				int os = (httpServletRequest.getHeader("os") != null && !httpServletRequest.getHeader("os").isEmpty())
						? Integer.parseInt(httpServletRequest.getHeader("os")) : 0;
				transition = new Transition(utilHandler.validateLanguage(httpServletRequest.getHeader("lang")),
						user.getId(), os, httpServletRequest.getHeader("version"));
			}

			servletRequest.setAttribute("transition", transition);

			filterChain.doFilter(servletRequest, servletResponse);

		} catch (Exception ex) {

			try {

				String response = "";
				if (ex instanceof AppException) {
					AppException appException = (AppException) ex;
					response = mapper.writeValueAsString(appException.getStatus());
				} else {
					response = mapper.writeValueAsString(new ResponseStatus(ResponseCode.GENERAL_FAILURE));
				}

				servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
				servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
				servletResponse.getWriter().write(response);

			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}

	}

}
