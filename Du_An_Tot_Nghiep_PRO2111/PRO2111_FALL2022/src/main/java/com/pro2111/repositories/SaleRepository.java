package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, String> {

	@Query("SELECT s FROM Sale s WHERE s.saleName = :name")
	public Sale findSaleByName(@Param("name") String name);

	/**
	 * Lấy danh sách khuyến mãi theo trạng thái
	 * 
	 * @param i
	 * @return
	 */
	@Query("SELECT s FROM Sale s WHERE s.status = :i and s.saleType = 0")
	public List<Sale> findByStatus(@Param("i") int i);

	/**
	 * Lấy danh sách khuyến mãi lớn
	 * 
	 * @param
	 * @return List<Sale>
	 */
	@Query("SELECT s FROM Sale s WHERE s.saleParent is null order by s.createdDate desc")
	public List<Sale> findAllSaleParent();

	/**
	 * Lấy saleChild theo saleParent
	 * 
	 * @param saleId
	 * @return List<Sale>
	 */
	// Tìm kiếm saleChile theo saleParent
	@Query("SELECT s FROM Sale s WHERE s.saleParent.saleId = :sid order by s.weekday desc")
	public List<Sale> findSaleChileBySaleParent(@Param("sid") String saleId);

	/**
	 * Lấy danh sách khuyến mãi con theo trạng thái
	 * 
	 * @param status i
	 * @return List<Sale>
	 */
	@Query("SELECT s FROM Sale s WHERE s.saleParent is not null and s.saleType = 1 and s.status = :i")
	public List<Sale> findSaleChildByStatus(@Param("i") int i);

	/**
	 * Xóa saleChild theo saleParent
	 * 
	 * @param saleParent
	 * @return
	 */
	@Modifying
	@Query("DELETE FROM Sale s WHERE s.saleParent = :sale")
	void deleteSaleChildBySaleParent(@Param("sale") Sale saleParent);
}
