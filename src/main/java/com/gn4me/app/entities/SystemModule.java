package com.gn4me.app.entities;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SystemModule {
	
	private int id;
	private String module;
	private String code;
	private int displayOrder;
	private Date insertDate;
	
	@JsonIgnore
	private boolean deleted;
	@JsonIgnore
	private String moduleAr;
	@JsonIgnore
	private String moduleEn;
	
}
