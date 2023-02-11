// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "CartDetail")
@Table(name = "cart_details")
public class CartDetail implements Serializable {

	/** Primary key. */
	protected static final String PK = "cartDetailId";

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "cart_detail_id", unique = true, nullable = false, length = 255)
	private String cartDetailId;

	@NotNull
	@Min(value = 1)
	@Column(nullable = false, precision = 10)
	private int quantity;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart carts;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "variant_id", nullable = false)
	private ProductVariant productVariants;

	/** Default constructor. */
	public CartDetail() {
		super();
	}

	/**
	 * Access method for cartDetailId.
	 *
	 * @return the current value of cartDetailId
	 */
	public String getCartDetailId() {
		return cartDetailId;
	}

	/**
	 * Setter method for cartDetailId.
	 *
	 * @param aCartDetailId the new value for cartDetailId
	 */
	public void setCartDetailId(String aCartDetailId) {
		cartDetailId = aCartDetailId;
	}

	/**
	 * Access method for quantity.
	 *
	 * @return the current value of quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Setter method for quantity.
	 *
	 * @param aQuantity the new value for quantity
	 */
	public void setQuantity(int aQuantity) {
		quantity = aQuantity;
	}

	/**
	 * Access method for createdDate.
	 *
	 * @return the current value of createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * Setter method for createdDate.
	 *
	 * @param aCreatedDate the new value for createdDate
	 */
	public void setCreatedDate(LocalDateTime aCreatedDate) {
		createdDate = aCreatedDate;
	}

	/**
	 * @return the carts
	 */
	public Cart getCarts() {
		return carts;
	}

	/**
	 * @param carts the carts to set
	 */
	public void setCarts(Cart carts) {
		this.carts = carts;
	}

	/**
	 * @return the productVariants
	 */
	public ProductVariant getProductVariants() {
		return productVariants;
	}

	/**
	 * @param productVariants the productVariants to set
	 */
	public void setProductVariants(ProductVariant productVariants) {
		this.productVariants = productVariants;
	}

	/**
	 * Compares the key for this instance with another CartDetails.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class CartDetails and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CartDetail)) {
			return false;
		}
		CartDetail that = (CartDetail) other;
		if (this.getCartDetailId() != that.getCartDetailId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another CartDetails.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof CartDetail))
			return false;
		return this.equalKeys(other) && ((CartDetail) other).equalKeys(this);
	}

	@Override
	public int hashCode() {
		long i;
		long result = 17;
		i = getCartDetailId().hashCode();
		result = 37 * result + i;
		return (int) result;
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[CartDetails |");
		sb.append(" cartDetailId=").append(getCartDetailId());
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
		ret.put("cartDetailId", getCartDetailId());
		return ret;
	}

}
