package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;

	private int adminId;
	
	@JsonIgnore
	private boolean deleted;

	private boolean hidden;

	private String icon;

	private Date insertDate;

	private String name;

	private int order;

	private boolean showInMenu;

	private Admin admin;

	private List<Content> contents;

	private List<Section> sections;

}