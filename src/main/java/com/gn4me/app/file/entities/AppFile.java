package com.gn4me.app.file.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gn4me.app.file.enums.ContentType;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppFile {
	

	private int id;
	@JsonIgnore
	private String generatedCode;
	private String name;
	private String extension;
	private String tags;
	private ContentType type;
	private int fileModuleId;
	@JsonIgnore
	private FileModule fileModule;
	@JsonIgnore
	public String getTypeStr() {
		return type.name();
	}
	
	public AppFile() {}
	
	public AppFile(AppFile file) {
		setId(file.getId());
		setGeneratedCode(file.getGeneratedCode());
		setName(file.getName());
		setExtension(file.getExtension());
		setTags(file.getTags());
		setType(file.getType());
		setFileModuleId(file.getFileModuleId());
	}
	
}
