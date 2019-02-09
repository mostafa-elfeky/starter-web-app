package com.gn4me.app.mail;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BasicMailInfo extends MailInfo {

	
	private String title;
	private String note;
	private List<String> elements = new ArrayList<String>();
	private List <MailAction> actions = new ArrayList<MailAction>();
	
	
}


