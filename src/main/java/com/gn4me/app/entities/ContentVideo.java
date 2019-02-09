package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentVideo implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String author;

	@JsonIgnore
	private boolean deleted;

	private String description;

	private Date insertDate;

	private int noOfViews;

	private String period;

	private String thumbnail;

	private String videoId;

	private Content content;

}