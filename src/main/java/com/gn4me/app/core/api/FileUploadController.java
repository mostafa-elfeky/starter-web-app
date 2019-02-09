package com.gn4me.app.core.api;

import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.response.GeneralResponse;
import com.gn4me.app.file.FileHandler;
import com.gn4me.app.file.FileUtil;
import com.gn4me.app.file.data.FileLoader;
import com.gn4me.app.file.entities.AppFile;
import com.gn4me.app.file.enums.ContentType;
import com.gn4me.app.log.Loggable;
import com.gn4me.app.log.Type;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/file")
@Loggable(Type = Type.CONTROLLER)
public class FileUploadController {

	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private FileUtil fileUtil;

	@ApiOperation(value = "Uploud Single file into a specific Category")
	@PostMapping(value = "/", headers = ("content-type=multipart/*"))
	public GeneralResponse uploadFile(
			@RequestParam(value = "file", required = true) MultipartFile uploadedFile,
			@RequestParam(value = "module") String module,
			@RequestParam(value = "tags", defaultValue="") String tags,
			Transition transition) throws Exception {
		
		
		return fileHandler.upload(uploadedFile, module, tags, transition);
		
	}
	
	@ApiOperation(value = "Uploud multi files into a specific Category")
	@PostMapping(value = "/multiple", headers = ("content-type=multipart/*"))
    public GeneralResponse uploadMultiFile(
    		@RequestParam("files") MultipartFile[] files,
    		@RequestParam(value = "category") String module,
    		Transition transition) throws Exception {
		
        return fileHandler.upload(files, module, transition);
        
    }

}
