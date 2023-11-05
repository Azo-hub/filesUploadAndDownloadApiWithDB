package com.filesUploadAndDownloadApi.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.filesUploadAndDownloadApi.Model.file;
import com.filesUploadAndDownloadApi.Service.FileService;

@RestController
@RequestMapping("/files")
public class FileResource {

	public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

	private final Cloudinary cloudinary = Singleton.getCloudinary();

	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles)
			throws IOException {

		List<String> filenames = new ArrayList<>();

		for (MultipartFile file : multipartFiles) {

			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			
			byte[] bytes = file.getBytes();
			  
			file currentFile = new file();
			  
			currentFile.setFilename(filename);
			  
			currentFile.setFileContent(bytes);
			  
			currentFile.setFileSize(bytes.length);
			  
			fileService.Save(currentFile);
			  
			filenames.add(filename);
			
		}

		return ResponseEntity.ok().body(filenames);

	}

	// Define a method to download files
	@GetMapping("/download/{filename}")
	public ResponseEntity<byte[]> downloadFiles(@PathVariable("filename") String filename)
			throws IOException, URISyntaxException {

		file dbFile = fileService.findByFilename(filename);
		 
		/*
		 * if (dbFile != null) {
		 * 
		 * throw new FileNotFoundException(filename + "was not found on the server");
		 * 
		 * }
		 */
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("File-Name", filename);
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + dbFile.getFilename());

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM).headers(httpHeaders).body(dbFile.getFileContent());
				
	}

}
