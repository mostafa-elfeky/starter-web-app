package com.gn4me.app.config.props;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import com.gn4me.app.file.enums.Uploader;


import lombok.Data;


@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProps {
	
	private Uploader uploader;
	private String uploadRootPath;
	private String downloadRootPath;
	private String fileSupportedType;
	private int fileMaxSize;
	private String imageSupportedType;
	
}
