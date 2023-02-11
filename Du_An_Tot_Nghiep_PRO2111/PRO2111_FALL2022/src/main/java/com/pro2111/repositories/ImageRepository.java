package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Image;

import com.pro2111.entities.ProductVariant;

public interface ImageRepository extends JpaRepository<Image, String> {
	/**
	 * Lấy ra list Image theo productVariant
	 * 
	 * @param productVariant
	 * @return
	 */
	@Query("SELECT i FROM Image i WHERE i.productVariants = :productVariant")
	List<Image> findByProduct(@Param("productVariant") ProductVariant productVariant);

	/**
	 * Tìm kiểm Image theo productVariant hoặc imagePath
	 * 
	 * @param productVariant
	 * @param imagePath
	 * @return
	 */
	Image findByProductVariantsLikeAndImagePathLike(ProductVariant productVariant, String imagePath);

	/**
	 * @param productVariants
	 * @return
	 */
	List<Image> findByProductVariantsLike(ProductVariant productVariants);

}
