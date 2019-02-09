package com.gn4me.app.entities;

import java.io.Serializable;
import lombok.Data;

import java.util.Date;

@Data
public class NotificationType implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String arMessage;

	private String arTitle;

	private String code;

	private boolean deleted;

	private boolean display;

	private String enMessage;

	private String enTitle;

	private String handler;

	private String icon;

	private String iconPath;

	private Date insertDate;

	private String mailMessage;

	private String mailSubject;

	private String type;

	private String typeIcon;

}