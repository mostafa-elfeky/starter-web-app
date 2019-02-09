package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;


@Data
public class UserNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int id;

	private String arTitle;

	private boolean deleted;

	private String enTitle;

	private String formattedArMsg;

	private String formattedEnMsg;

	private boolean hide;

	private String icon;

	private Date insertDate;

	private boolean mailSent;

	private int notificationTypeId;

	private int notifiedUserId;

	private boolean seen;

	private boolean sent;

	private int userId;

}