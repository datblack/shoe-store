package com.pro2111.restcontrollers.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.service.CachingService;
import com.pro2111.service.UploadService;

@CrossOrigin("*")
@RestController
@RequestMapping("admin/rest/photo-resource")
public class UploadImageRestController {

	private static final Logger logger = Logger.getLogger(UploadImageRestController.class.getName());
	@Autowired
	UploadService uploadService;

	@Autowired
	CachingService cachingService;

	@Autowired
	ServletContext app;

	@PostMapping("/upload/file")
	public JsonNode upload(@PathVariable("file") MultipartFile file) {
		File savedFile = uploadService.save(file);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", savedFile.getName());
		node.put("size", savedFile.length());
		return node;
	}

	@PostMapping("/upload/multiple/files")
	public ResponseEntity<JsonNode> uploadData(@RequestParam("multipleFiles") MultipartFile[] files) throws Exception {
		ObjectMapper mapper;
		ObjectNode node = null;
		try {

			if (files == null || files.length == 0) {
				throw new RuntimeException("You must select at least one file for uploading");
			}

			for (int i = 0; i < files.length; i++) {

				File savedFile = uploadService.save(files[i]);
				mapper = new ObjectMapper();
				node = mapper.createObjectNode();
				node.put("name", savedFile.getName());
				node.put("size", savedFile.length());

				cachingService.evictAllCaches();
			}
		} catch (Exception e) {

		}
		return ResponseEntity.ok(node);

	}

	@GetMapping("/get-all-file")
	public List<String> getAllFile() {
		List<String> lstFile = new ArrayList<String>();
		try {
			File[] listOfFiles = uploadService.getAllFile();
			if (listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
//						System.out.println("File " + listOfFiles[i].getName());
						lstFile.add(listOfFiles[i].getName());
					} else if (listOfFiles[i].isDirectory()) {
//						System.out.println("Directory " + listOfFiles[i].getName());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		cachingService.evictAllCaches();
		return lstFile;
	}

	@DeleteMapping("/delete-file/{fileName}")
	public Boolean deleteFile(@PathVariable("fileName") String fileName) {
		return uploadService.removeFile(fileName);
	}

}
