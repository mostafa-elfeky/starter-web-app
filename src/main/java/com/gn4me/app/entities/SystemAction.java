package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SystemAction implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int id;

	private String actionCode;

	private boolean deleted;

	private Date insertDate;

	private String name;

	private List<ContentAction> contentActions;

}