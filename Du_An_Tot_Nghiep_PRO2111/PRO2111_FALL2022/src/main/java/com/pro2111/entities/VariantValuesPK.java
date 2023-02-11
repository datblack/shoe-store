// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Embeddable
public class VariantValuesPK implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({
		@JoinColumn(name = "Option_id", nullable = false),
		@JoinColumn(name = "Product_id", nullable = false)
	})
	private ProductOption productOptions;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Variant_id", nullable = false)
	private ProductVariant productVariants;


	public ProductOption getProductOptions() {
		return productOptions;
	}


	public void setProductOptions(ProductOption productOptions) {
		this.productOptions = productOptions;
	}


	public ProductVariant getProductVariants() {
		return productVariants;
	}


	public void setProductVariants(ProductVariant productVariants) {
		this.productVariants = productVariants;
	}

//	private ProductOption productOptions2;
//	
//	@ManyToOne(optional = false)
	
	 

}
