package com.gn4me.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;


@Data
public class Gender implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean deleted;

	private String genderAr;

	private String genderEn;

	private Date insertDate;

	private List<User> users;

}