package com.filesUploadAndDownloadApi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filesUploadAndDownloadApi.Model.file;

/**
 * @author Azo-hub
 * @github (https://github.com/Azo-hub)
 * @since 2020
 */
public interface FileRepository extends JpaRepository <file, Long>{

	file findByFilename(String filename);

}
