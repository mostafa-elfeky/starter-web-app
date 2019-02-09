package com.gn4me.app.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ContentImage implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean deleted;

	private String imageName;

	private Date insertDate;

	private Content content;

}