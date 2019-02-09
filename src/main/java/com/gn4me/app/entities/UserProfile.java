package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;


@Data
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int badgeCount;

	private boolean featured;

	private boolean notificationOff;

	private int userId;

}