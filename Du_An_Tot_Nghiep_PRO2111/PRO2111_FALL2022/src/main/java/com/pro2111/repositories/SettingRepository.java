/**
 * DATN_FALL2022, 2022
 * SettingRepository.java, BUI_QUANG_HIEU
 */
package com.pro2111.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro2111.entities.Setting;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface SettingRepository extends JpaRepository<Setting, Integer> {

}
