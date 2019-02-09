package com.gn4me.app.file;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gn4me.app.config.props.FileProps;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.response.GeneralResponse;
import com.gn4me.app.entities.response.ResponseStatus;
import com.gn4me.app.file.data.FileDao;
import com.gn4me.app.file.entities.AppFile;
import com.gn4me.app.file.entities.FileInfo;
import com.gn4me.app.file.entities.FileResponse;
import com.gn4me.app.file.entities.ImageSize;
import com.gn4me.app.file.enums.ContentType;
import com.gn4me.app.file.enums.ImageSizeEnum;
import com.gn4me.app.log.LogHelper;



@Service
public class FileHandler {
	
	@Autowired
	FileUploader fileUploader;
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	FileUtil fileUtil;
	
	@Autowired
	FileProps props;
	
	@Autowired
	LogHelper logger;

	public GeneralResponse upload(MultipartFile file, String module, String tags, Transition transition) throws Exception {
		
		FileResponse response = new FileResponse();
		Map<String, String> files = new HashMap<String, String>();
		final String defaultFile = "DEFAULT";	
		
		FileInfo info = fileUtil.getUploadFileInfo(file, module, tags, transition);
		
		String uploadPath = props.getUploadRootPath() + info.getRelativePath() + "/";
		String downloadPath = props.getDownloadRootPath() + info.getRelativePath() + "/";
		String pureDownloadPath = props.getDownloadRootPath() + info.getRelativePath();
	 	
		//fileUtil.getFileInfo(file, category, asFile, transition);
		if(info.getType().equals(ContentType.IMAGE)) {
			
			logger.log("File type is image, Going to resize image with module confiuration", transition);
			
			logger.log("Save image application specific sizes ", transition);

			for(ImageSizeEnum size : info.getFileModule().getSizes()) {
				
				byte[] processedImage = null;
				
				if(!size.equals(ImageSizeEnum.PURE)) {
					processedImage = fileUtil.resizeImage(file.getBytes(), size, transition);
				}

				info.setFilePath(uploadPath + size.toString().toLowerCase());
				
				fileUploader.upload(info, processedImage, transition);
				
				info.setFilePath(downloadPath + size.toString().toLowerCase());
				if(size.equals(info.getFileModule().getDefaultSize())) {
					files.put(defaultFile.toLowerCase(), info.getFullPath());
				} else {
					files.put(size.toString().toLowerCase(), info.getFullPath());
				}
				
			}
			
			logger.log("Save images with Custom sizes ", transition);
			
			for(ImageSize size : info.getFileModule().getCustomSizes()) {
				
				byte[] processedImage = fileUtil.resizeImage(file.getBytes(), size, transition);
				info.setFilePath(uploadPath + size.getCode());
				
				fileUploader.upload(info, processedImage, transition);
				
				info.setFilePath(downloadPath + size.getCode());
				files.put(size.getCode().toLowerCase(), info.getFullPath());

			}
			
			logger.log("Save thumbnail image", transition);
			
			byte[] processedImage = fileUtil.resizeImage(file.getBytes(), ImageSizeEnum.THUMBNAIL, transition);
			info.setFilePath(uploadPath + ImageSizeEnum.THUMBNAIL.toString().toLowerCase());
			fileUploader.upload(info, processedImage, transition);
			
			info.setFilePath(downloadPath + ImageSizeEnum.THUMBNAIL.toString().toLowerCase());
			info.setThumbnail(info.getFullPath());
			
		} else {
			
			logger.log("File type is file, Going to upload it", transition);
			
			info.setFilePath(uploadPath);
			fileUploader.upload(info, file.getBytes(), transition);
			
			info.setFilePath(pureDownloadPath);
			files.put(defaultFile.toLowerCase(), info.getFullPath());
			
		}
		
		
		if(files.get(defaultFile) == null) {
			
			logger.log("As there is No default file yet we will set the first One as default.", transition);
			
			String firstKey = files.keySet().stream().findFirst().get();
			String fiestPath = files.remove(firstKey);
			files.put(defaultFile.toLowerCase(), fiestPath);
		}
		
		info.setFiles(files);
		
		AppFile fileInfo = info;
		
		logger.log("File info generated as info, " + info, transition);
		
		logger.log("Going to save this file in DB, file, " + fileInfo, transition);
		
		
		AppFile appFile = fileDao.save(fileInfo, transition);
		
		if(appFile.getId() > 0) {
			logger.log("File Saved Successfully into DB, " + fileInfo, transition);
			response.setInfo(info);
			response.setResponseStatus(new ResponseStatus(ResponseCode.SUCCESS));
		} else {
			logger.log("Faild To save this File" + fileInfo, transition);
			response.setResponseStatus(new ResponseStatus(ResponseCode.NO_DATA_SAVED, transition));
		}

		return response;
	}
	
	public FileResponse upload(MultipartFile[] files,  String module, Transition transition) throws Exception {
		return null;
	}
	
}
