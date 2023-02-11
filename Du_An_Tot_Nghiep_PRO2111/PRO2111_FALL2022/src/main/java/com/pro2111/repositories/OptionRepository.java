package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;

public interface OptionRepository extends JpaRepository<Option, Integer> {

	/**
	 * Lấy Option theo OptionName
	 * 
	 * @param nameOption
	 * @return
	 */
	Option findByOptionNameLike(String nameOption);

	/**
	 * Lấy list Option theo status
	 * 
	 * @param status
	 * @return
	 */
	List<Option> findByStatusLike(int status);

	/**
	 * Lấy list Option chưa tồn tại trong Product
	 * 
	 * @param product
	 * @return
	 */
	@Query("SELECT o FROM Option o WHERE o.status = 1 AND NOT EXISTS (SELECT po FROM ProductOption po WHERE po.options = o AND po.products = :product)")
	List<Option> findOptionNotExistsProductOption(@Param("product") Product product);

	/**
	 * Lấy list Option theo OptionName gần đúng
	 * 
	 * @param name
	 * @return
	 */
	@Query("SELECT o FROM Option o WHERE o.optionName LIKE %:name%")
	List<Option> findByApproximateName(@Param("name") String name);

	/**
	 * Lấy list Option theo status và isShow
	 * 
	 * @param status
	 * @param isShow
	 * @return
	 */
	List<Option> findByStatusLikeAndIsShowLike(int status, int isShow);

}
