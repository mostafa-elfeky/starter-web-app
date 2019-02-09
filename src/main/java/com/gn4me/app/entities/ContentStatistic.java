package com.gn4me.app.entities;

import java.io.Serializable;
import lombok.Data;

import java.util.Date;

@Data
public class ContentStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int count;

	private boolean deleted;

	private Date insertDate;

	private String statName;


	private Content content;

	
}