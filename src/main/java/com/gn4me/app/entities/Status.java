package com.gn4me.app.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Status {
	
	private int id;
	private String status;
	private String code;
	private String module;
	
	@JsonIgnore
	private String statusAr;
	@JsonIgnore
	private String statusEn;
	
}
