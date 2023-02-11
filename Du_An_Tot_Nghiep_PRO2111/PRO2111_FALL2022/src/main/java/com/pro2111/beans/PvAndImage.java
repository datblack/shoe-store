/**
 * DATN_FALL2022, 2022
 * PvAndImage.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import com.pro2111.entities.ProductVariant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PvAndImage {

	private ProductVariant productVariant;
	private String imagePath;
}
