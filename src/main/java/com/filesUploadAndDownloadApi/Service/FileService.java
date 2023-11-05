package com.filesUploadAndDownloadApi.Service;

import java.net.URL;

import com.filesUploadAndDownloadApi.Model.file;

/**
 * @author Azo-hub
 * @github (https://github.com/Azo-hub)
 * @since 2020
 */
public interface FileService {
	
	file findByFilename(String filename);
	
	void Save(file file);

}
