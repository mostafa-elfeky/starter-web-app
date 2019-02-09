package com.gn4me.app.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gn4me.app.core.service.UserService;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.User;
import com.gn4me.app.entities.enums.Security;
import com.gn4me.app.entities.response.GeneralResponse;
import com.gn4me.app.log.Loggable;
import com.gn4me.app.log.Type;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
@Loggable(Type = Type.CONTROLLER)
public class AuthController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Sign in User Based on email and password")
	@PostMapping("/signin")
	public GeneralResponse signin(@RequestParam String email, @RequestParam String password, Transition transition)
			throws Exception {
		
		return userService.signin(email, password, transition);
		
	}

	@PostMapping("/signup")
	public GeneralResponse signup(@RequestBody User user, 
			Transition transition) throws Exception {
		
		GeneralResponse response = null;
		
		try {
			response  =  userService.signup(user, transition);
		} catch(Exception exp) {
			exp.printStackTrace();
			//throw new AppException(new ResponseStatus(ResponseCode.GENERAL_FAILURE), transition);
			throw exp;
		}
		
		return response;
	}

	@PostMapping("/refresh-key")
	public GeneralResponse refreshKey(
			@RequestHeader(value = "Authorization") String bearerToken,
			Transition transition)
			throws Exception {

		String token = "";

		if (bearerToken != null && bearerToken.startsWith(Security.BEARER_PREFIX.getValue())) {
			token = bearerToken.substring(7, bearerToken.length());
		}

		return userService.refreshKey(token, transition);
	}
	
	@ApiOperation(value = "Forget password send email to get an email with reset process")
	@PostMapping("/forget-password")
	public GeneralResponse forgetPassword(
			@RequestParam String email, Transition transition) throws Exception {
		
		return userService.forgetPassword(email, transition);
		
	}
	
	@ApiOperation(value = "Forget password send email to get an email with reset process")
	@PostMapping("/reset-password")
	public GeneralResponse resetPassword(
			@RequestParam String token,
			@RequestParam String password,
			Transition transition) throws Exception {
		
		return userService.resetPassword(token, password, transition);
		
	}

}
