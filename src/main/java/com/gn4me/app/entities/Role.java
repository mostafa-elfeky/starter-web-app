package com.gn4me.app.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Role implements Serializable{
	
	private static final long serialVersionUID = -6321755184302643167L;
	
	private int id;
	private String role;
	private String code;
	private List<Privilege> privileges;
	
	@JsonIgnore
	private List<User> users;
	@JsonIgnore
	private Date insertDate;
	@JsonIgnore
	private boolean deleted;
	@JsonIgnore
	private String roleAr;
	@JsonIgnore
	private String roleEn;
	
	public Role(){}
	
	public Role(int id){
		this.id = id;
	}
	
}
