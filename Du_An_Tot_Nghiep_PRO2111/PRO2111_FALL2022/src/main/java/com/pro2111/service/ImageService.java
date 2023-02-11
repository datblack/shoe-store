package com.pro2111.service;

import java.util.List;

import javax.validation.Valid;

import com.pro2111.beans.PvAndImage;
import com.pro2111.entities.Image;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;

public interface ImageService {
	/**
	 * Lấy tất cả image
	 * 
	 * @return
	 */
	public List<Image> findAll();

	/**
	 * Lấy image theo id
	 * 
	 * @param id
	 * @return
	 */
	public Image findById(String id);

	/**
	 * Thêm mới image
	 * 
	 * @param images
	 * @return
	 */
	public Image create(Image images);

	/**
	 * Cập nhật image
	 * 
	 * @param images
	 * @return
	 */
	public Image update(Image images);

	/**
	 * Xoá image theo id
	 * 
	 * @param id
	 * @return
	 */
	public Image delete(String id);

	/**
	 * Lấy image theo productVariant
	 * 
	 * @param productVariant
	 * @return
	 */
	public List<Image> findByProductVariant(ProductVariant productVariant);

	/**
	 * Xoá image theo productVariant
	 * 
	 * @param pvAndImage
	 */
	public Image deleteByProductVariant(PvAndImage pvAndImage);

	/**
	 * lấy Image theo product
	 * 
	 * @param product
	 * @return
	 */
	public List<Image> findByProduct(Product product);

	/**
	 * Tạo list image
	 * @param images
	 * @return
	 */
	public List<Image> createList(List<Image> images);

}
