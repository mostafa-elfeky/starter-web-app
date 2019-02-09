package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

@Data
public class SystemNotificationAttributeValue implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int attributeId;

	private boolean deleted;

	private Date insertDate;

	private int systemNotificationId;

	private String value;
	
}