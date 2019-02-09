package com.gn4me.app.entities.filters;



import java.io.Serializable;

import lombok.Data;

@Data
public class ListCategoriesFilter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4907700892814816456L;
	
	private boolean showInMenu;
}
