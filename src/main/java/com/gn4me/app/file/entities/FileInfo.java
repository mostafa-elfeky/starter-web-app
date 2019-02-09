package com.gn4me.app.file.entities;


import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInfo extends AppFile {

	@JsonIgnore
	private String fullPath;
	
	@JsonIgnore
	private String filePath;
	
	@JsonIgnore
	private StringBuilder relativePath;
	
	private String thumbnail;
	private Map<String, String> files;
	
	public FileInfo() {}
	
	public FileInfo(AppFile file) {
		super(file);
	}
	
	public void setFilePath(String path) {
		this.filePath = path;
		this.fullPath = path + "/" + this.getGeneratedCode();
	}
	
}
