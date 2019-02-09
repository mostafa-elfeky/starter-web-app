package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean deleted;

	private Date insertDate;

	private String name;

	private String sectionCode;
	
	private int categoryId;
	
	private int contentTypeId;
	
	private Category category;

	private ContentType contentType;

	private SectionListingContentType sectionListingContentType;

	private SectionPage sectionPage;

	}