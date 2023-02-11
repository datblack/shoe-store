// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro2111.utils.FormateString;
import com.pro2111.validations.products.UniqueProductName;

@Entity
@Table(name = "products")
@Indexed
public class Product implements Serializable {

	/** Primary key. */
	protected static final String PK = "productId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Product_id", unique = true, nullable = false, precision = 10)
	private int productId;

	@NotBlank(message = "{Product.productName.NotBlank}")
	@UniqueProductName(message = "{Product.productName.UniqueProductName}")
	@Length(min = 5, max = 100,message = "{Product.productName.Length}")
	@Field(termVector = TermVector.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "Product_name", nullable = false, length = 100)
	private String productName;

	@NotNull(message = "{Product.status.NotNull}")
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "Status", nullable = false, precision = 10)
	private int status;

	@JsonIgnore
	@OneToMany(mappedBy = "products")
	private Set<ProductVariant> productVariants;

	@JsonIgnore
	@OneToMany(mappedBy = "products")
	private Set<ProductOption> productOptions;

	/** Default constructor. */
	public Product() {
		super();
	}

	/**
	 * Access method for productId.
	 *
	 * @return the current value of productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Setter method for productId.
	 *
	 * @param aProductId the new value for productId
	 */
	public void setProductId(int aProductId) {
		productId = aProductId;
	}

	/**
	 * Access method for productName.
	 *
	 * @return the current value of productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Setter method for productName.
	 *
	 * @param aProductName the new value for productName
	 */
	public void setProductName(String aProductName) {
		productName = FormateString.UpperCaseString(aProductName);
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
	 * Access method for productVariants.
	 *
	 * @return the current value of productVariants
	 */
	public Set<ProductVariant> getProductVariants() {
		return productVariants;
	}

	/**
	 * Setter method for productVariants.
	 *
	 * @param aProductVariants the new value for productVariants
	 */
	public void setProductVariants(Set<ProductVariant> aProductVariants) {
		productVariants = aProductVariants;
	}

	/**
	 * Access method for productOptions.
	 *
	 * @return the current value of productOptions
	 */
	public Set<ProductOption> getProductOptions() {
		return productOptions;
	}

	/**
	 * Setter method for productOptions.
	 *
	 * @param aProductOptions the new value for productOptions
	 */
	public void setProductOptions(Set<ProductOption> aProductOptions) {
		productOptions = aProductOptions;
	}

	/**
	 * Compares the key for this instance with another Products.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Products and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Product)) {
			return false;
		}
		Product that = (Product) other;
		if (this.getProductId() != that.getProductId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Products.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Product))
			return false;
		return this.equalKeys(other) && ((Product) other).equalKeys(this);
	}

	/**
	 * Returns a hash code for this instance.
	 *
	 * @return Hash code
	 */
	@Override
	public int hashCode() {
		int i;
		int result = 17;
		i = getProductId();
		result = 37 * result + i;
		return result;
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[Products |");
		sb.append(" productId=").append(getProductId());
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Return all elements of the primary key.
	 *
	 * @return Map of key names to values
	 */
	public Map<String, Object> getPrimaryKey() {
		Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
		ret.put("productId", Integer.valueOf(getProductId()));
		return ret;
	}

}
