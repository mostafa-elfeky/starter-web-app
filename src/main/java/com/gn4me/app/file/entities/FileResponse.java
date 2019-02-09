package com.gn4me.app.file.entities;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gn4me.app.entities.response.GeneralResponse;

@Component
@Qualifier("fileUploadResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Scope("prototype")
public class FileResponse extends GeneralResponse {

	private FileInfo info;

	public FileInfo getInfo() {
		return info;
	}

	public void setInfo(FileInfo info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "FileUploadResponse [info=" + info + "]";
	}

	
}
