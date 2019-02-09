package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;


@Data
public class SystemNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String arMessage;

	private boolean deleted;

	private String enMessage;

	private String icon;

	private Date insertDate;

	private int notificationTypeId;

	private Date publishDate;

	private boolean published;

	private int userId;

	
}