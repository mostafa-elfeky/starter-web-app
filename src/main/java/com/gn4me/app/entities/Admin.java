package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;
import java.util.Date;
import java.util.List;


@Data
public class Admin implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean active;

	private boolean deleted;

	private String email;

	private String firstName;

	private Date insertDate;

	private String lastName;

	private String photo;

	private String userName;

	private String userPassword;

	private Role role;

	private List<Category> categories;

	private List<Content> contents;

}