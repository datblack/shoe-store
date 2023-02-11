// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "variant_values")
public class VariantValue implements Serializable {
	
	@EmbeddedId
	protected VariantValuesPK variantValuesPK;

	@NotNull
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "Status", nullable = false, precision = 10)
	private int status;
	@ManyToOne(optional = false)
	@JoinColumn(name = "Value_id", nullable = false)
	private OptionValue optionValues;

	@ManyToOne(optional = false, cascade = javax.persistence.CascadeType.MERGE)
//	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumns({ @JoinColumn(name = "Option_id", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "Product_id", nullable = false, insertable = false, updatable = false) })
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductOption productOptions;
//	private ProductOption productOptions2;
//	@ManyToOne(optional = false)

	@ManyToOne(optional = false, cascade = javax.persistence.CascadeType.ALL)
//	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "Variant_id", nullable = false, insertable = false, updatable = false)
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private ProductVariant productVariants;

	/** Default constructor. */
	public VariantValue() {
		super();
	}

	/**
	 * Access method for status.
	 *
	 * @return the current value of status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter method for status.
	 *
	 * @param aStatus the new value for status
	 */
	public void setStatus(int aStatus) {
		status = aStatus;
	}

	/**
	 * Access method for optionValues.
	 *
	 * @return the current value of optionValues
	 */
	public OptionValue getOptionValues() {
		return optionValues;
	}

	/**
	 * Setter method for optionValues.
	 *
	 * @param aOptionValues the new value for optionValues
	 */
	public void setOptionValues(OptionValue aOptionValues) {
		optionValues = aOptionValues;
	}

	/**
	 * Access method for productOptions2.
	 *
	 * @return the current value of productOptions2
	 */
//	public ProductOption getProductOptions2() {
//		return productOptions2;
//	}
//
//	/**
//	 * Setter method for productOptions2.
//	 *
//	 * @param aProductOptions2 the new value for productOptions2
//	 */
//	public void setProductOptions2(ProductOption aProductOptions2) {
//		productOptions2 = aProductOptions2;
//	}

	/**
	 * Access method for productOptions.
	 *
	 * @return the current value of productOptions
	 */
	public ProductOption getProductOptions() {
		return productOptions;
	}

	/**
	 * Setter method for productOptions.
	 *
	 * @param aProductOptions the new value for productOptions
	 */
	public void setProductOptions(ProductOption aProductOptions) {
		productOptions = aProductOptions;
	}

	/**
	 * Access method for productVariants.
	 *
	 * @return the current value of productVariants
	 */
	public ProductVariant getProductVariants() {
		return productVariants;
	}

	/**
	 * Setter method for productVariants.
	 *
	 * @param aProductVariants the new value for productVariants
	 */
	public void setProductVariants(ProductVariant aProductVariants) {
		productVariants = aProductVariants;
	}

}
