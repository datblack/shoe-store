/**
 * DATN_FALL2022, 2022
 * HistorySettingRepositoty.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pro2111.entities.History;
import com.pro2111.entities.Setting;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface HistoryRepository extends JpaRepository<History, String> {

	/**
	 * Lấy list History theo Setting
	 * 
	 * @param setting
	 * @return
	 */
	@Query("SELECT h FROM History h WHERE h.setting =:setting ORDER BY h.createdDate DESC")
	List<History> getAllHistoryBySetting(@Param("setting") Setting setting);

}
