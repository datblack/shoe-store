package com.pro2111.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.PvAndImage;
import com.pro2111.entities.Image;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.repositories.ImageRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.service.ImageService;
import com.pro2111.utils.Constant;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ProductVariantRepository productVariantRepository;

	/**
	 * Lấy tất cả list ảnh
	 */
	@Override
	public synchronized List<Image> findAll() {
		return imageRepository.findAll();
	}

	/**
	 * Lấy ra 1 Image theo id
	 */
	@Override
	public synchronized Image findById(String id) {
		return imageRepository.findById(id).get();
	}

	/**
	 * Thêm mới một Image
	 */
	@Override
	public synchronized Image create(Image image) {
		image.setStatus(Constant.STATUS_TRUE_IMAGE);
		return imageRepository.save(image);
	}

	@Override
	public synchronized Image update(Image images) {
		return imageRepository.save(images);
	}

	/**
	 * Xoá Image theo id
	 */
	@Override
	public synchronized Image delete(String id) {
		Image image = imageRepository.findById(id).get();
		imageRepository.delete(image);
		return image;
	}

	/**
	 * Lấy ra 1 list Image theo productVariant
	 */
	@Override
	public synchronized List<Image> findByProductVariant(ProductVariant productVariant) {
		return imageRepository.findByProduct(productVariant);
	}

	/**
	 * Xoá 1 Image theo ProductVariant
	 */
	@Override
	public synchronized Image deleteByProductVariant(PvAndImage pvAndImage) {
		Image image = imageRepository.findByProductVariantsLikeAndImagePathLike(pvAndImage.getProductVariant(),
				pvAndImage.getImagePath());
		imageRepository.delete(image);
		return image;
	}

	/**
	 * Lấy ra 1 list image theo product
	 */
	@Override
	public synchronized List<Image> findByProduct(Product product) {
		List<Image> images = new ArrayList<Image>();
		// Lấy ra 1 list ProductVariant theo product
		List<ProductVariant> variants = productVariantRepository.findByProductsLike(product);
		// Duyệt mảng variants
		variants.forEach(pv -> {
			List<Image> imageByPv = imageRepository.findByProduct(pv);
			if (imageByPv.size() > 0) {
				images.add(imageByPv.get(0));
			}

		});
		return images;
	}

	@Override
	@Transactional
	public List<Image> createList(List<Image> images) {
		// Xóa ảnh cũ của productVariant
		List<Image> listOld = imageRepository.findByProductVariantsLike(images.get(0).getProductVariants());
		imageRepository.deleteAll(listOld);
		// Tạo list ảnh mới
		return imageRepository.saveAll(images);
	}

}
