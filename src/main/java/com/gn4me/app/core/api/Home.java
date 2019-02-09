package com.gn4me.app.core.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gn4me.app.core.service.UserService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class Home {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public void welecom(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.sendRedirect(request.getContextPath() + "/swagger-ui.html");
	}

}
