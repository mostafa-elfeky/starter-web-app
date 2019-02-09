package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;


@Data
public class UserRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean deleted;

	private Date insertDate;

	private int responseAdminId;

	private Date responseDate;

	private Content content;

	private SystemStatus systemStatus;

	private UserRequestType userRequestType;

	private User user;

}