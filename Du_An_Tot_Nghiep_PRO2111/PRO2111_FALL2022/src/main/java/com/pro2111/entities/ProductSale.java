// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "ProductSale")
@Table(name = "product_sales")
public class ProductSale implements Serializable {

	/** Primary key. */
	protected static final String PK = "productSaleId";

	@Id
	@Column(name = "product_sale_id", unique = true, nullable = false, length = 50)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String productSaleId;
	@Column(precision = 10)
	private int status;
	@ManyToOne(optional = false)
	@JoinColumn(name = "variant_id", nullable = false)
	private ProductVariant productVariants;
	@ManyToOne(optional = false)
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sales;

	/** Default constructor. */
	public ProductSale() {
		super();
	}

	/**
	 * Access method for productSaleId.
	 *
	 * @return the current value of productSaleId
	 */
	public String getProductSaleId() {
		return productSaleId;
	}

	/**
	 * Setter method for productSaleId.
	 *
	 * @param aProductSaleId the new value for productSaleId
	 */
	public void setProductSaleId(String aProductSaleId) {
		productSaleId = aProductSaleId;
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

	/**
	 * Access method for sales.
	 *
	 * @return the current value of sales
	 */
	public Sale getSales() {
		return sales;
	}

	/**
	 * Setter method for sales.
	 *
	 * @param aSales the new value for sales
	 */
	public void setSales(Sale aSales) {
		sales = aSales;
	}

	/**
	 * Compares the key for this instance with another ProductSale.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class ProductSale and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductSale)) {
			return false;
		}
		ProductSale that = (ProductSale) other;
		Object myProductSaleId = this.getProductSaleId();
		Object yourProductSaleId = that.getProductSaleId();
		if (myProductSaleId == null ? yourProductSaleId != null : !myProductSaleId.equals(yourProductSaleId)) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another ProductSale.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProductSale))
			return false;
		return this.equalKeys(other) && ((ProductSale) other).equalKeys(this);
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
		if (getProductSaleId() == null) {
			i = 0;
		} else {
			i = getProductSaleId().hashCode();
		}
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
		StringBuffer sb = new StringBuffer("[ProductSale |");
		sb.append(" productSaleId=").append(getProductSaleId());
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
		ret.put("productSaleId", getProductSaleId());
		return ret;
	}

}
