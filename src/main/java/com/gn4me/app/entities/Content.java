package com.gn4me.app.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int userId;
	
	private int categoryId;

	@JsonIgnore
	private boolean deleted;

	private boolean featured;

	private boolean hide;

	private Date insertDate;

	private Date publishDate;

	private String text;

	private String title;
	
	private int contentTypeId;
	
	private String userActionCode;
	
	private boolean savedStatus;

	private Admin admin;

	private Category category;

	private Status status;

	private ContentType contentType;

	private User user;
	
	ContentVideo contentVideo;

	private List<ContentAction> contentActions;

	private List<ContentImage> contentImages;

	private List<ContentReport> contentReports;

	private List<ContentSharing> contentSharings;

	private HashMap<String, Integer> contentStatistics;

	private List<ContentVideo> contentVideos;

	private List<UserRequest> userRequests;

	private List<UserSavedContent> userSavedContents;

}