package com.filesUploadAndDownloadApi.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileResource {

	public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

	@PostMapping("/upload")
	public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles)
			throws IOException {

		List<String> filenames = new ArrayList<>();

		for (MultipartFile file : multipartFiles) {
			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			Path fileStorage = Paths.get(DIRECTORY, filename).toAbsolutePath().normalize();
			Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
			filenames.add(filename);
		}

		return ResponseEntity.ok().body(filenames);

	}

}
