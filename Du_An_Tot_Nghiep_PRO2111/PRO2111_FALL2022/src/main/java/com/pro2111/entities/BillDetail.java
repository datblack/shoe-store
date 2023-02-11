// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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

import lombok.Data;

@Entity
@Table(name = "bill_details")
@Data
public class BillDetail implements Serializable {

	/** Primary key. */
//	protected static final String PK = "detailBillId";

	public BillDetail(int quantity, BigDecimal price, BigDecimal totalMoney, Bill bills, Integer status, BigDecimal tax,
			ProductVariant productVariants) {
		this.quantity = quantity;
		this.price = price;
		this.totalMoney = totalMoney;
		this.bills = bills;
		this.status = status;
		this.tax = tax;
		this.productVariants = productVariants;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "detail_bill_id", unique = true, nullable = false, length = 50)
	private String detailBillId;
	@Min(value = 0)
	@NotNull
	@Column(name = "quantity", nullable = false, precision = 10)
	private int quantity;

	@Min(value = 0)
	@Column(name = "price", nullable = false, precision = 10)
	private BigDecimal price;

	@Min(value = 0)
	@Column(name = "total_money", nullable = false, precision = 10)
	private BigDecimal totalMoney;

	@ManyToOne(optional = false)
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bills;

	@Column(name = "note", nullable = false, precision = 255)
	private String note;

	@Column(name = "status", nullable = false, precision = 10)
	private Integer status;

	@Column(name = "tax", nullable = false, precision = 10)
	private BigDecimal tax;

//	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "variant_id", nullable = false)
	private ProductVariant productVariants;

	@ManyToOne
	@JoinColumn(name = "bill_detail_id_parent", nullable = true)
	private BillDetail billDetail;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_confirm", nullable = false)
	private User userConfirm;

	/** Default constructor. */
	public BillDetail() {
		super();
	}

	/**
	 * Compares the key for this instance with another DetailBills.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class DetailBills and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BillDetail)) {
			return false;
		}
		BillDetail that = (BillDetail) other;
		if (this.getDetailBillId() != that.getDetailBillId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another DetailBills.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BillDetail))
			return false;
		return this.equalKeys(other) && ((BillDetail) other).equalKeys(this);
	}

	/**
	 * Returns a hash code for this instance.
	 *
	 * @return Hash code
	 */
	@Override
	public int hashCode() {
		long i;
		long result = 17;
		i = getDetailBillId().hashCode();
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
		StringBuffer sb = new StringBuffer("[DetailBills |");
		sb.append(" detailBillId=").append(getDetailBillId());
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
		ret.put("detailBillId", getDetailBillId());
		return ret;
	}

}
