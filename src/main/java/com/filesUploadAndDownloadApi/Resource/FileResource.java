package com.filesUploadAndDownloadApi.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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

@RestController
@RequestMapping("/files")
public class FileResource {

	private final Cloudinary cloudinary = Singleton.getCloudinary();

	public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

	@PostMapping("/upload")
	@Async
	public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles)
			throws IOException {

		/*
		 * List<String> filenames = new ArrayList<>(); List<String> imageUrls = new
		 * ArrayList<>();
		 * 
		 * for (MultipartFile file : multipartFiles) { String filename =
		 * StringUtils.cleanPath(file.getOriginalFilename());
		 * 
		 * Path fileStorage = Paths.get(DIRECTORY,
		 * filename).toAbsolutePath().normalize(); Files.copy(file.getInputStream(),
		 * fileStorage, StandardCopyOption.REPLACE_EXISTING);
		 * 
		 * 
		 * byte[] bytes = file.getBytes(); String newFileName =
		 * "fileUploadandDownloadApi-" + filename;
		 * 
		 * uploading image file to cloudinary Map uploadResult =
		 * cloudinary.uploader().upload(bytes, ObjectUtils.asMap("invalidate", true));
		 * 
		 * String publicId = uploadResult.get("public_id").toString();
		 * 
		 * renaming uploaded file to cloudinary Map uploadResultRename =
		 * cloudinary.uploader().rename(publicId, newFileName,
		 * ObjectUtils.asMap("overwrite", "true"));
		 * 
		 * // System.out.println(uploadResultRename.get("public_id")); String imageUrl =
		 * uploadResultRename.get("secure_url").toString();
		 * 
		 * filenames.add(filename); imageUrls.add(imageUrl); }
		 */
		
		List<String> filenames = new ArrayList<>();

		for (MultipartFile file : multipartFiles) {
			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			Path fileStorage = Paths.get(DIRECTORY, filename).toAbsolutePath().normalize();
			Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
			filenames.add(filename);
		}
		
		return ResponseEntity.ok().body(filenames);

	}

	// Define a method to download files
	@GetMapping("/download/{filename}")
	@Async
	public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {

		Path filePath = Paths.get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);

		if (!Files.exists(filePath)) {
			throw new FileNotFoundException(filename + "was not found on the server");
		}

		Resource resource = new UrlResource(filePath.toUri());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("File-Name", filename);
		httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
				.headers(httpHeaders).body(resource);
	}

}
