package com.gn4me.app.file;

import com.gn4me.app.entities.Transition;
import com.gn4me.app.file.entities.FileInfo;



public interface FileUploader {
	
	
	public boolean upload(FileInfo info, byte[] bytes, Transition transition) throws Exception;
		
	public boolean delete(FileInfo info, Transition transition) throws Exception;
	
}
