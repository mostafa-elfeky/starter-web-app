package com.gn4me.app.entities;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Privilege {
	
	private int id;
	private String privilege;
	private String code;
	
	@JsonIgnore
	private Date insertDate;
	@JsonIgnore
	private boolean deleted;
	@JsonIgnore
	private String privilegeAr;
	@JsonIgnore
	private String privilegeEn;
	
}
