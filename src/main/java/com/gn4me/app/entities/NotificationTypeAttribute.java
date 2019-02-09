package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;


@Data
public class NotificationTypeAttribute implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String attributeType;

	private byte deleted;

	private Date insertDate;

	private String name;

	private int notificationTypeId;

	

}