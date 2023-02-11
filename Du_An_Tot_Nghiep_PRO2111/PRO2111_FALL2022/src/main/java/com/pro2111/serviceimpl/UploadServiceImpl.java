package com.pro2111.serviceimpl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pro2111.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
	@Autowired
	ServletContext app;

	@Override
	public File save(MultipartFile file) {
		File dir = new File(app.getRealPath("/assets/images"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String s = System.currentTimeMillis() + file.getOriginalFilename();
		String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));

		try {
			File savedFile = new File(dir, name);
			file.transferTo(savedFile);
			System.out.println(savedFile.getAbsolutePath());
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public File[] getAllFile() {
		File dir = new File(app.getRealPath("/assets/images"));
		File[] listOfFiles = dir.listFiles();
		return listOfFiles;

	}

	@Override
	public Boolean removeFile(String fileName) {
		File file = new File(app.getRealPath("/assets/images/" + fileName));
		if (file.delete()) {
			return true;
		} else {
			return false;
		}
	}

}
