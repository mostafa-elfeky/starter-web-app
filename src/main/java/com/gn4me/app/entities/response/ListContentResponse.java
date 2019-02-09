package com.gn4me.app.entities.response;

import java.util.List;
import com.gn4me.app.entities.Content;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListContentResponse extends GeneralResponse{
	
	private List<Content> contentList;
}
