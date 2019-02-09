package com.gn4me.app.config.props;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.gn4me.app.file.enums.Uploader;

import lombok.Getter;
import lombok.ToString;
import lombok.AccessLevel;


@Component
@Getter @ToString(exclude="env")
public class FileProps {
	
	@Autowired
	@Getter(AccessLevel.NONE)
	private Environment env;
	
	private Uploader uploader;
	private String uploadRootPath;
	private String downloadRootPath;
	private String fileSupportedType;
	private int fileMaxSize;
	private String imageSupportedType;
	
	@PostConstruct
	public void refresh() {
		this.uploader = Uploader.valueOf(env.getProperty("file.uploader"));
		this.uploadRootPath = env.getProperty("file.root-path.upload");
		this.downloadRootPath = env.getProperty("file.root-path.download");
		this.fileSupportedType = env.getProperty("file.supported-types");
		this.fileMaxSize = Integer.parseInt(env.getProperty("file.size.max"));
		this.imageSupportedType = env.getProperty("file.image.supported-types");
	}
	
}
