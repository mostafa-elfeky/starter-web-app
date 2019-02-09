package com.gn4me.app.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gn4me.app.core.service.UserService;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.response.GeneralResponse;
import com.gn4me.app.log.Loggable;
import com.gn4me.app.log.Type;
import com.gn4me.app.util.AppException;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/auth")
@Loggable(Type = Type.CONTROLLER)
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Update User Password")
	@PostMapping("/update-password")
	public GeneralResponse updatePassword(
			@RequestParam(name="old-password") String oldPassword, 
			@RequestParam(name="new-password") String newPassword,
			Transition transition) throws Exception {
		
		GeneralResponse response = null;
		
		try {
			response = userService.updatePassword(oldPassword, newPassword, transition);
		} catch (AppException appExp) {
			appExp.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return response;
	}

}
