package com.gn4me.app.file.entities;

import com.gn4me.app.file.enums.ImageSizeEnum;

import lombok.Data;

@Data
public class FileModule {

	private int id;
	private String module;
	private ImageSizeEnum[] sizes;
	private ImageSize[] customSizes;
	private int maxSize;
	private int compressLevel;
	private ImageSizeEnum defaultSize;
	
	
}
