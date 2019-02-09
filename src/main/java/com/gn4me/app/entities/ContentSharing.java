package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

@Data
public class ContentSharing implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean deleted;

	private Date insertDate;

	private Content content;

	private User user;

	
}