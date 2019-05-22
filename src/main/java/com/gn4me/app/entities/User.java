package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	
	private String image;
	private int badgeCount;
	private boolean featured;
	private boolean notificationOFF;
	private String token;
	private Date insertDate;
	private boolean trusted;
	
	private List<Role> roles;

//	private int statusId;
	private Status status;
	private String[] rights;
	
	private String secToken;
	
	@JsonIgnore
	private boolean autoCreated;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonIgnore
	private Date tokenExpiryDate;
	
	@JsonIgnore
	private boolean deleted;

	private Gender gender;

	
	@JsonIgnore
	public String[] getRightsFromRules() {
		String[] rights = null;
		try {
			
			if(this.roles != null) {
				int length = this.roles.size();
				rights = new String[length];
				for (int i = 0; i < length; i++) {
					rights[i] = this.roles.get(i).getCode();
				}
			} 
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rights;
	}
	
	
}