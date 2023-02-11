// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product_options")
public class ProductOption implements Serializable {
	@EmbeddedId
	protected ProductOptionPK productOptionPK;

	@NotNull(message = "{ProductOption.status.NotNull}")
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "Status", nullable = false, precision = 10)
	private int status;

	@NotNull(message = "{ProductOption.options.NotNull}")
	@ManyToOne(optional = false)
	@JoinColumn(name = "Option_id", nullable = false, insertable = false, updatable = false)
	private Option options;

	@NotNull(message = "{ProductOption.products.NotNull}")
	@ManyToOne(optional = false)
	@JoinColumn(name = "Product_id", nullable = false, insertable = false, updatable = false)
	private Product products;
//	@OneToMany(mappedBy = "productOptions2")
//	private Set<VariantValues> variantValues2;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productOptions")
	private Set<VariantValue> variantValues;

	/** Default constructor. */
	public ProductOption() {
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
	 * Access method for options.
	 *
	 * @return the current value of options
	 */
	public Option getOptions() {
		return options;
	}

	/**
	 * Setter method for options.
	 *
	 * @param aOptions the new value for options
	 */
	public void setOptions(Option aOptions) {
		options = aOptions;
	}

	/**
	 * Access method for products.
	 *
	 * @return the current value of products
	 */
	public Product getProducts() {
		return products;
	}

	/**
	 * Setter method for products.
	 *
	 * @param aProducts the new value for products
	 */
	public void setProducts(Product aProducts) {
		products = aProducts;
	}

	/**
	 * Access method for variantValues2.
	 *
	 * @return the current value of variantValues2
	 */
//	public Set<VariantValues> getVariantValues2() {
//		return variantValues2;
//	}
//
//	/**
//	 * Setter method for variantValues2.
//	 *
//	 * @param aVariantValues2 the new value for variantValues2
//	 */
//	public void setVariantValues2(Set<VariantValues> aVariantValues2) {
//		variantValues2 = aVariantValues2;
//	}

	/**
	 * Access method for variantValues.
	 *
	 * @return the current value of variantValues
	 */
	public Set<VariantValue> getVariantValues() {
		return variantValues;
	}

	/**
	 * Setter method for variantValues.
	 *
	 * @param aVariantValues the new value for variantValues
	 */
	public void setVariantValues(Set<VariantValue> aVariantValues) {
		variantValues = aVariantValues;
	}

}
