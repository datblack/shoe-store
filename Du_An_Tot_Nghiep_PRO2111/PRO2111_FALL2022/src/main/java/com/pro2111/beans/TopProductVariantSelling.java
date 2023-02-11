/**
 * DATN_FALL2022, 2022
 * TopProductVariantSelling.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.pro2111.entities.ProductVariant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TopProductVariantSelling {
	@Id
	private ProductVariant productVariant;
	private long quantity;
}
