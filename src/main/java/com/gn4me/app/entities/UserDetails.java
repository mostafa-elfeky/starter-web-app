package com.gn4me.app.entities;

import java.util.Date;

import com.gn4me.app.entities.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserDetails extends User{

	private String address;
	private String mobileNo;
	private String teleNo;
	private int zipCode;
	private Date birthDate;
	
	//add Additional User Data...
			
}
