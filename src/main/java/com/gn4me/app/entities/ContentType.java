package com.gn4me.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentType implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	
	@JsonIgnore
	private boolean deleted;

	private Date insertDate;

	private String name;

	private String typeCode;

	private List<Content> contents;

	private List<Section> sections;

}