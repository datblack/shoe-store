package com.pro2111.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	/**
	 * Tạo file
	 * 
	 * @param file
	 * @return
	 */
	File save(MultipartFile file);

	/**
	 * Lấy tất cả file
	 * 
	 * @return
	 */
	File[] getAllFile();

	/**
	 * Xóa file theo name
	 * 
	 * @param fileName
	 * @return
	 */
	Boolean removeFile(String fileName);
}
