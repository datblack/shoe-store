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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "carts")
public class Cart implements Serializable {

	/** Primary key. */
	protected static final String PK = "cartId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id", unique = true, nullable = false, precision = 10)
	private int cartId;
	@Column(name = "status", nullable = false, precision = 10)
	private int status;
	@JsonIgnore
	@OneToMany(mappedBy = "carts")
	private Set<CartDetail> detailCarts;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User users;

	/** Default constructor. */
	public Cart() {
		super();
	}

	/**
	 * Access method for cartId.
	 *
	 * @return the current value of cartId
	 */
	public int getCartId() {
		return cartId;
	}

	/**
	 * Setter method for cartId.
	 *
	 * @param aCartId the new value for cartId
	 */
	public void setCartId(int aCartId) {
		cartId = aCartId;
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
	 * Access method for detailCarts.
	 *
	 * @return the current value of detailCarts
	 */
	public Set<CartDetail> getDetailCarts() {
		return detailCarts;
	}

	/**
	 * Setter method for detailCarts.
	 *
	 * @param aDetailCarts the new value for detailCarts
	 */
	public void setDetailCarts(Set<CartDetail> aDetailCarts) {
		detailCarts = aDetailCarts;
	}

	/**
	 * Access method for users.
	 *
	 * @return the current value of users
	 */
	public User getUsers() {
		return users;
	}

	/**
	 * Setter method for users.
	 *
	 * @param aUsers the new value for users
	 */
	public void setUsers(User aUsers) {
		users = aUsers;
	}

	/**
	 * Compares the key for this instance with another Carts.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Carts and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Cart)) {
			return false;
		}
		Cart that = (Cart) other;
		if (this.getCartId() != that.getCartId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Carts.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Cart))
			return false;
		return this.equalKeys(other) && ((Cart) other).equalKeys(this);
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
		i = getCartId();
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
		StringBuffer sb = new StringBuffer("[Carts |");
		sb.append(" cartId=").append(getCartId());
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
		ret.put("cartId", Integer.valueOf(getCartId()));
		return ret;
	}

}
