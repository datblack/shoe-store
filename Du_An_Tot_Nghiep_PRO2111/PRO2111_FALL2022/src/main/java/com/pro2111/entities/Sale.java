// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro2111.utils.FormateString;

@Entity(name = "Sale")
@Table(name = "sales")
public class Sale implements Serializable {

	/** Primary key. */
	protected static final String PK = "saleId";

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "sale_id", unique = true, nullable = false, length = 36)
	private String saleId;

	@Column(name = "sale_name", length = 255)
	private String saleName;

	@Column(name = "discount_type", precision = 10, columnDefinition = "integer default 0")
	private int discountType;

	@Column(name = "discount", precision = 12)
	private BigDecimal discount;

	@Column(name = "created_date", nullable = true)
	private LocalDateTime createdDate;

	@Column(name = "start_date", nullable = true)
	private LocalDateTime startDate;

	@Column(name = "end_date", nullable = true)
	private LocalDateTime endDate;

	@Column(name = "weekday")
	private int weekday;

	@Column(name = "start_at", nullable = true)
	private LocalTime startAt;

	@Column(name = "end_at", nullable = true)
	private LocalTime endAt;

	@ManyToOne
	@JoinColumn(name = "sale_parent", nullable = true)
	private Sale saleParent;

	@Column(name = "sale_type", columnDefinition = "integer default 0")
	private int saleType;

	@Min(value = 0)
	@Column(name = "status", precision = 10)
	private int status;

	@ManyToOne
	@JoinColumn(name = "created_user")
	private User createdUser;

	@ManyToOne
	@JoinColumn(name = "updated_user")
	private User updatedUser;

	@Column(name = "updated_date")
	private LocalDateTime updateDate;

	@JsonIgnore
	@OneToMany(mappedBy = "sales")
	private Set<ProductSale> productSales;

	/** Default constructor. */
	public Sale() {
		super();
	}

	/**
	 * Access method for saleId.
	 *
	 * @return the current value of saleId
	 */
	public String getSaleId() {
		return saleId;
	}

	/**
	 * Setter method for saleId.
	 *
	 * @param aSaleId the new value for saleId
	 */
	public void setSaleId(String aSaleId) {
		saleId = aSaleId;
	}

	/**
	 * Access method for saleName.
	 *
	 * @return the current value of saleName
	 */
	public String getSaleName() {
		return saleName;
	}

	/**
	 * Setter method for saleName.
	 *
	 * @param aSaleName the new value for saleName
	 */
	public void setSaleName(String aSaleName) {
		saleName = FormateString.UpperCaseString(aSaleName);
	}

	/**
	 * Access method for saleType.
	 *
	 * @return the current value of saleType
	 */
	public int getDiscountType() {
		return discountType;
	}

	/**
	 * Setter method for saleType.
	 *
	 * @param aSaleType the new value for saleType
	 */
	public void setDiscountType(int aSaleType) {
		discountType = aSaleType;
	}

	/**
	 * Access method for discount.
	 *
	 * @return the current value of discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * Setter method for discount.
	 *
	 * @param aDiscount the new value for discount
	 */
	public void setDiscount(BigDecimal aDiscount) {
		discount = aDiscount;
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
	 * Access method for startDate.
	 *
	 * @return the current value of startDate
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}

	/**
	 * Setter method for startDate.
	 *
	 * @param aStartDate the new value for startDate
	 */
	public void setStartDate(LocalDateTime aStartDate) {
		startDate = aStartDate;
	}

	/**
	 * Access method for endDate.
	 *
	 * @return the current value of endDate
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}

	/**
	 * Setter method for endDate.
	 *
	 * @param aEndDate the new value for endDate
	 */
	public void setEndDate(LocalDateTime aEndDate) {
		endDate = aEndDate;
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
	 * @return the productSale
	 */
	public Set<ProductSale> getProductSales() {
		return productSales;
	}

	/**
	 * @param productSale the productSale to set
	 */
	public void setProductSales(Set<ProductSale> productSales) {
		this.productSales = productSales;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public LocalTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}

	public LocalTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}

	public Sale getSaleParent() {
		return saleParent;
	}

	public void setSaleParent(Sale sale) {
		this.saleParent = sale;
	}

	public int getSaleType() {
		return saleType;
	}

	public void setSaleType(int saleType) {
		this.saleType = saleType;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public User getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(User updatedUser) {
		this.updatedUser = updatedUser;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Compares the key for this instance with another Sale.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Sale and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Sale)) {
			return false;
		}
		Sale that = (Sale) other;
		Object mySaleId = this.getSaleId();
		Object yourSaleId = that.getSaleId();
		if (mySaleId == null ? yourSaleId != null : !mySaleId.equals(yourSaleId)) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Sale.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Sale))
			return false;
		return this.equalKeys(other) && ((Sale) other).equalKeys(this);
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
		if (getSaleId() == null) {
			i = 0;
		} else {
			i = getSaleId().hashCode();
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
		StringBuffer sb = new StringBuffer("[Sale |");
		sb.append(" saleId=").append(getSaleId());
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
		ret.put("saleId", getSaleId());
		return ret;
	}

}
