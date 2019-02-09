package com.gn4me.app.entities.response;

import com.gn4me.app.entities.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends GeneralResponse {

	private User user;
	
}
