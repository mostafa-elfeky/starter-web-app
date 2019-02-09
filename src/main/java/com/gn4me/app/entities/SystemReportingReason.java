package com.gn4me.app.entities;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SystemReportingReason implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String arReason;

	private boolean deleted;

	private int displayOrder;

	private String enReason;

	private Date insertDate;

	private List<ContentReport> contentReports;

	private List<UserReport> userReports;

	
}