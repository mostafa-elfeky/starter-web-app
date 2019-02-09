package com.gn4me.app.entities.response;

import java.util.List;

import com.gn4me.app.entities.Category;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListCategoriesResponse extends GeneralResponse{
	
	private List<Category> categoriesList;
}
