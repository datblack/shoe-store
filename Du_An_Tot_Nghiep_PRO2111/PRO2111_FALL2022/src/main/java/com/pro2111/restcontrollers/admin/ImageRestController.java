package com.pro2111.restcontrollers.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.PvAndImage;
import com.pro2111.entities.Image;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.service.ImageService;

@CrossOrigin("*")
@RestController
@RequestMapping("admin/rest/image")
public class ImageRestController {

	@Autowired
	private ImageService imageService;

	/**
	 * Lấy danh sách image
	 * 
	 * @return list image
	 */
	@GetMapping()
	public ResponseEntity<List<Image>> getAll() {
		// sử dụng phương thức findAll bên service
		//
		return ResponseEntity.ok(imageService.findAll());
	}

	/**
	 * Tạo mới
	 * @param image
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<Image> create(@Valid @RequestBody Image image) {
		try {
			return ResponseEntity.ok(imageService.create(image));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	/**
	 * Tạo mới 1 list image
	 */
	@PostMapping("store-list-image")
	public ResponseEntity<List<Image>> createListImage(@Valid @RequestBody List<Image> images) {
		try {
			return ResponseEntity.ok(imageService.createList(images));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy image theo id
	 * 
	 * @param
	 * @return
	 */
	@GetMapping("{id}")
	public ResponseEntity<Image> getOne(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok(imageService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	/**
	 * Cập nhật image
	 * 
	 * @param id
	 * @param image
	 * @return
	 */
	@PutMapping("{id}")
	public ResponseEntity<Image> update(@PathVariable("id") String id, @Valid @RequestBody Image image) {
		try {
			image.setImagesId(id);
			return ResponseEntity.ok(imageService.create(image));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * lấy Image theo productVariant
	 * 
	 * @param productVariant
	 * @return
	 */
	@GetMapping("find-by-product/{idProduct}")
	public ResponseEntity<List<Image>> findByProductVariant(@PathVariable("idProduct") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(imageService.findByProductVariant(productVariant));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy image theo product
	 * 
	 * @param product
	 * @return
	 */
	@GetMapping("find-by-product-origin/{idProduct}")
	public ResponseEntity<List<Image>> findByProduct(@PathVariable("idProduct") Product product) {
		try {
			return ResponseEntity.ok(imageService.findByProduct(product));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xoá 1 image
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<Image> delete(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok(imageService.delete(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xoá ảnh theo ProductVariant
	 * 
	 * @param pvAndImage
	 * @return
	 */
	@DeleteMapping("delete-by-product-variant")
	public ResponseEntity<Image> deleteByProductVariant(@PathVariable PvAndImage pvAndImage) {
		try {
			return ResponseEntity.ok(imageService.deleteByProductVariant(pvAndImage));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
