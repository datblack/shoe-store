// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bills")
public class Bill implements Serializable {

	/** Primary key. */
	protected static final String PK = "billId";

	@Id
	@Column(name = "bill_id", unique = true, nullable = false, length = 50)
	private String billId;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "success_date", nullable = false)
	private LocalDate successDate;
	
	@Column(name = "cancel_date", nullable = false)
	private LocalDate cancelDate;

	@Column(name = "total_money", nullable = false, precision = 10)
	private BigDecimal totalMoney;

	@Column(name = "shipping_fee", nullable = false, precision = 10)
	private BigDecimal shippingFee;

	@Min(value = 0)
	@Column(name = "discount", nullable = false, precision = 10)
	private int discount;

	@Column(name = "payments", nullable = false, precision = 10)
	private int payments;

	@Column(name = "division_id", nullable = false, precision = 10)
	private int divisionId;

	@Column(name = "division_name", nullable = false, length = 100)
	private String divisionName;

	@Column(name = "district_id", nullable = false, precision = 10)
	private int districtId;

	@Column(name = "district_name", nullable = false, length = 100)
	private String districtName;

	@Column(name = "ward_id", nullable = false, precision = 10)
	private int wardId;

	@Column(name = "ward_name", nullable = false, length = 100)
	private String wardName;

	@Column(name = "ward_code", nullable = false, length = 100)
	private String wardCode;

	@Column(name = "address_detail", length = 255)
	private String address;

	@Column(name = "phone", length = 20)
	private String phone;

	@Column(name = "note", length = 100)
	private String note;

	@Column(name = "status", nullable = false, precision = 10)
	private int status;

	@Column(name = "bill_type", nullable = false, precision = 10)
	private int billType;

	@JsonIgnore
	@OneToMany(mappedBy = "bills")
	private Set<BillDetail> detailBills;

	@ManyToOne
	@JoinColumn(name = "Customer_id")
	private User customer;

	@ManyToOne
	@JoinColumn(name = "User_id")
	private User user;

	/** Default constructor. */
	public Bill() {
		super();
	}

	/**
	 * Access method for billId.
	 *
	 * @return the current value of billId
	 */
	public String getBillId() {
		return billId;
	}

	/**
	 * Setter method for billId.
	 *
	 * @param aBillId the new value for billId
	 */
	public void setBillId(String aBillId) {
		billId = aBillId;
	}

	/**
	 * Access method for datetime.
	 *
	 * @return the current value of datetime
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * Setter method for datetime.
	 *
	 * @param aDatetime the new value for datetime
	 */
	public void setCreatedDate(LocalDateTime aCreatedDate) {
		createdDate = aCreatedDate;
	}

	/**
	 * @return the successDate
	 */
	public LocalDate getSuccessDate() {
		return successDate;
	}

	/**
	 * @param successDate the successDate to set
	 */
	public void setSuccessDate(LocalDate successDate) {
		this.successDate = successDate;
	}
	
	

	/**
	 * @return the cancelDate
	 */
	public LocalDate getCancelDate() {
		return cancelDate;
	}

	/**
	 * @param cancelDate the cancelDate to set
	 */
	public void setCancelDate(LocalDate cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * Access method for totalMoney.
	 *
	 * @return the current value of totalMoney
	 */
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	/**
	 * Setter method for totalMoney.
	 *
	 * @param aTotalMoney the new value for totalMoney
	 */
	public void setTotalMoney(BigDecimal aTotalMoney) {
		totalMoney = aTotalMoney;
	}

	/**
	 * @return the shippingFee
	 */
	public BigDecimal getShippingFee() {
		return shippingFee;
	}

	/**
	 * @param shippingFee the shippingFee to set
	 */
	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}

	/**
	 * Access method for discount.
	 *
	 * @return the current value of discount
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * Setter method for discount.
	 *
	 * @param aDiscount the new value for discount
	 */
	public void setDiscount(int aDiscount) {
		discount = aDiscount;
	}

	/**
	 * Access method for payments.
	 *
	 * @return the current value of payments
	 */
	public int getPayments() {
		return payments;
	}

	/**
	 * Setter method for payments.
	 *
	 * @param aPayments the new value for payments
	 */
	public void setPayments(int aPayments) {
		payments = aPayments;
	}

	/**
	 * @return the divisionId
	 */
	public int getDivisionId() {
		return divisionId;
	}

	/**
	 * @param divisionId the divisionId to set
	 */
	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the districtId
	 */
	public int getDistrictId() {
		return districtId;
	}

	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * @return the wardId
	 */
	public int getWardId() {
		return wardId;
	}

	/**
	 * @param wardId the wardId to set
	 */
	public void setWardId(int wardId) {
		this.wardId = wardId;
	}

	/**
	 * @return the wardName
	 */
	public String getWardName() {
		return wardName;
	}

	/**
	 * @param wardName the wardName to set
	 */
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	/**
	 * @return the wardCode
	 */
	public String getWardCode() {
		return wardCode;
	}

	/**
	 * @param wardCode the wardCode to set
	 */
	public void setWardCode(String wardCode) {
		this.wardCode = wardCode;
	}

	/**
	 * Access method for address.
	 *
	 * @return the current value of address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter method for address.
	 *
	 * @param aAddress the new value for address
	 */
	public void setAddress(String aAddress) {
		address = aAddress;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Access method for note.
	 *
	 * @return the current value of note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Setter method for note.
	 *
	 * @param aNote the new value for note
	 */
	public void setNote(String aNote) {
		note = aNote;
	}

	/**
	 * Access method for oderStatus.
	 *
	 * @return the current value of oderStatus
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter method for oderStatus.
	 *
	 * @param aOderStatus the new value for oderStatus
	 */
	public void setStatus(int aStatus) {
		status = aStatus;
	}

	/**
	 * @return the billType
	 */
	public int getBillType() {
		return billType;
	}

	/**
	 * @param billType the billType to set
	 */
	public void setBillType(int billType) {
		this.billType = billType;
	}

	/**
	 * Access method for detailBills.
	 *
	 * @return the current value of detailBills
	 */
	public Set<BillDetail> getDetailBills() {
		return detailBills;
	}

	/**
	 * Setter method for detailBills.
	 *
	 * @param aDetailBills the new value for detailBills
	 */
	public void setDetailBills(Set<BillDetail> aDetailBills) {
		detailBills = aDetailBills;
	}

	/**
	 * Access method for customers.
	 *
	 * @return the current value of customers
	 */
	public User getCustomers() {
		return customer;
	}

	/**
	 * Setter method for customers.
	 *
	 * @param aCustomers the new value for customers
	 */
	public void setCustomers(User aCustomer) {
		customer = aCustomer;
	}

	/**
	 * Access method for users.
	 *
	 * @return the current value of users
	 */
	public User getUsers() {
		return user;
	}

	/**
	 * Setter method for users.
	 *
	 * @param aUsers the new value for users
	 */
	public void setUsers(User aUsers) {
		user = aUsers;
	}

	/**
	 * Compares the key for this instance with another Bills.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Bills and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Bill)) {
			return false;
		}
		Bill that = (Bill) other;
		Object myBillId = this.getBillId();
		Object yourBillId = that.getBillId();
		if (myBillId == null ? yourBillId != null : !myBillId.equals(yourBillId)) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Bills.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Bill))
			return false;
		return this.equalKeys(other) && ((Bill) other).equalKeys(this);
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
		if (getBillId() == null) {
			i = 0;
		} else {
			i = getBillId().hashCode();
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
		StringBuffer sb = new StringBuffer("[Bills |");
		sb.append(" billId=").append(getBillId());
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
		ret.put("billId", getBillId());
		return ret;
	}

}
