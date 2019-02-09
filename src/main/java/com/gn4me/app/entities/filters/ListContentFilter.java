package com.gn4me.app.entities.filters;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListContentFilter extends ListFilter{
	
	private int userId;
	
	private String sectionCode;
	
	private int contentTypeId;
	
	private int categoryId;
	
	private String viewName;
}
