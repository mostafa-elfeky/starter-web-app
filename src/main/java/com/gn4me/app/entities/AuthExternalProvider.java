package com.gn4me.app.entities;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AuthExternalProvider {
	
	private int id;
	private String name;
	private String code;
	private String icon;
	private int displayOrder;
	private Date insertDate;
	
	@JsonIgnore
	private boolean deleted;
	@JsonIgnore
	private String nameAr;
	@JsonIgnore
	private String nameEn;
	
}
