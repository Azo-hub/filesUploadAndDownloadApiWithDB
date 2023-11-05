package com.filesUploadAndDownloadApi.Service.Impl;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filesUploadAndDownloadApi.Model.file;
import com.filesUploadAndDownloadApi.Repository.FileRepository;
import com.filesUploadAndDownloadApi.Service.FileService;

/**
 * @author Azo-hub
 * @github (https://github.com/Azo-hub)
 * @since 2020
 */

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileRepository fileRepository;

	@Override
	public file findByFilename(String filename) {
		
		return fileRepository.findByFilename(filename);
	}

	@Override
	public void Save(file file) {
		
		fileRepository.save(file);
		
	}

}
