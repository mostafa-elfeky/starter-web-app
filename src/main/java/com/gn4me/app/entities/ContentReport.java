package com.gn4me.app.entities;

import java.io.Serializable;
import lombok.Data;

import java.util.Date;

@Data
public class ContentReport implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean deleted;

	private Date insertDate;

	private String reasonText;

	private Content content;

	private SystemReportingReason systemReportingReason;

	private User user;

}