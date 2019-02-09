package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserRequestType implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private boolean deleted;

	private Date insertDate;

	private String name;

	private String possibleActions;

	private String requestTypeCode;

	private List<UserRequest> userRequests;

}