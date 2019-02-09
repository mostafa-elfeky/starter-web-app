package com.gn4me.app.entities;



import com.gn4me.app.entities.enums.Language;


import lombok.Getter;
import lombok.ToString;

@Getter @ToString(exclude="info")
public class Transition {

	private final long id;
	private final int userId;
	private final Language language;
	private final int os;
	private final String version;
	
	public Transition() {
		super();
		this.id = System.currentTimeMillis();
		this.language = Language.EN;
		this.userId = 0;
		this.os=0;
		this.version="";
	}
	
	public Transition(Language language,int os,String version) {
		super();
		this.id = System.currentTimeMillis();
		this.language = language;
		this.userId = 0;
		this.os=os;
		this.version=version;
	}
	
	public Transition(Language language, int userId,int os,String version) {
		super();
		this.id = System.currentTimeMillis();
		this.language = language;
		this.userId = userId;
		this.os=os;
		this.version=version;
	}

}
