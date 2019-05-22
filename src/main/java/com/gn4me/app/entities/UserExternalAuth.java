package com.gn4me.app.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gn4me.app.entities.User;

import lombok.Data;

@Data
public class UserExternalAuth {

	private int id;
	private int externalUserId;
	private String firstName;
	private String lastName;
	private String email;
	private String image;
	private boolean trusted;
	
	private int userId;
	private User user;
	
	private int authExtProviderId;
	private AuthExternalProvider authExtProvider;
	
	private int statusId;
	private Status status;
	
	private Date insertDate;
	@JsonIgnore
	private boolean deleted;
			
}
