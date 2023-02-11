// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro2111.utils.FormateString;
import com.pro2111.validations.users.RegexUserPhone;
import com.pro2111.validations.users.UniqueUserEmail;
import com.pro2111.validations.users.UniqueUserPhone;

@Entity
@Table(name = "users", indexes = { @Index(name = "users_Email_IX", columnList = "email", unique = true),
		@Index(name = "users_Phone_IX", columnList = "phone", unique = true) })

public class User implements Serializable, UserDetails {

	/** Primary key. */
	protected static final String PK = "userId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false, precision = 10)
	private int userId;

	@NotBlank(message = "{User.fullName.NotBlank}")
	@Length(max = 100, message = "{User.fullName.Length}")
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;

	@NotBlank(message = "{User.email.NotBlank}")
	@Length(max = 255, message = "{User.email.Length}")
	@Email(message = "{User.email.Email}")
	@UniqueUserEmail(message = "{User.email.UniqueUserEmail}")
	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@NotBlank(message = "{User.password.NotBlank}")
	@Length(min = 8, max = 100, message = "{User.password.Length}")
	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@NotBlank(message = "{User.phone.NotBlank}")
	@Length(min = 10, max = 20, message = "{User.phone.Length}")
	@RegexUserPhone(message = "{User.phone.RegexUserPhone}")
	@UniqueUserPhone(message = "{User.phone.UniqueUserPhone}")
	@Column(name = "phone", unique = true, nullable = false, length = 20)
	private String phone;

	@NotNull(message = "{User.sex.NotNull}")
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "sex", nullable = false, precision = 10)
	private int sex;

	@NotNull(message = "{User.role.NotNull}")
	@Column(name = "role", nullable = false, precision = 10)
	private int role;

	@Column(name = "avatar", length = 200)
	private String avatar;

	@JsonIgnore
	@NotNull(message = "{User.otp.NotNull}")
	@Column(name = "otp", nullable = false, precision = 10)
	private int otp;

	@NotNull(message = "{User.status.NotNull}")
	@Column(name = "status", nullable = false, precision = 10)
	private int status;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@JsonIgnore
	@OneToMany(mappedBy = "userCreate")
	private Set<ProductVariant> productVariants;

	@JsonIgnore
	@OneToMany(mappedBy = "userEdit")
	private Set<ProductVariant> productVariants2;

	@JsonIgnore
	@OneToMany(mappedBy = "users")
	private Set<Cart> carts;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Bill> bills;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private Set<Bill> bill2;

	@JsonIgnore
	@OneToMany(mappedBy = "userEdit")
	private Set<Setting> settings;

	@JsonIgnore
	@OneToMany(mappedBy = "userConfirm")
	private Set<BillDetail> billDetails;

	@Column(name = "division_id", nullable = false, precision = 10)
	private int divisionId;

	@Column(name = "division_name", nullable = false, length = 100)
	private String divisionName;

	@Column(name = "district_id", nullable = false, precision = 10)
	private int districtId;

	@Column(name = "district_name", nullable = false, length = 100)
	private String districtName;

	@Column(name = "ward_name", nullable = false, length = 100)
	private String wardName;

	@Column(name = "ward_code", nullable = false, length = 45)
	private String wardCode;

	@Column(name = "address_detail", length = 255)
	private String address;

	/** Default constructor. */
	public User() {
		super();
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName.trim();
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return FormateString.LowerCaseString(email);
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email.trim();
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
		this.phone = phone.trim();
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * @return the role
	 */
	public int getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(int role) {
		this.role = role;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the otp
	 */
	public int getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(int otp) {
		this.otp = otp;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the productVariants
	 */
	public Set<ProductVariant> getProductVariants() {
		return productVariants;
	}

	/**
	 * @param productVariants the productVariants to set
	 */
	public void setProductVariants(Set<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

	/**
	 * @return the productVariants2
	 */
	public Set<ProductVariant> getProductVariants2() {
		return productVariants2;
	}

	/**
	 * @param productVariants2 the productVariants2 to set
	 */
	public void setProductVariants2(Set<ProductVariant> productVariants2) {
		this.productVariants2 = productVariants2;
	}

	/**
	 * @return the carts
	 */
	public Set<Cart> getCarts() {
		return carts;
	}

	/**
	 * @param carts the carts to set
	 */
	public void setCarts(Set<Cart> carts) {
		this.carts = carts;
	}

	/**
	 * @return the bills
	 */
	public Set<Bill> getBills() {
		return bills;
	}

	/**
	 * @param bills the bills to set
	 */
	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}

	/**
	 * @return the bill2
	 */
	public Set<Bill> getBill2() {
		return bill2;
	}

	/**
	 * @param bill2 the bill2 to set
	 */
	public void setBill2(Set<Bill> bill2) {
		this.bill2 = bill2;
	}

	/**
	 * @return the settings
	 */
	public Set<Setting> getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(Set<Setting> settings) {
		this.settings = settings;
	}

	/**
	 * @return the billDetails
	 */
	public Set<BillDetail> getBillDetails() {
		return billDetails;
	}

	/**
	 * @param billDetails the billDetails to set
	 */
	public void setBillDetails(Set<BillDetail> billDetails) {
		this.billDetails = billDetails;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Compares the key for this instance with another Users.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Users and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof User)) {
			return false;
		}
		User that = (User) other;
		if (this.getUserId() != that.getUserId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Users.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof User))
			return false;
		return this.equalKeys(other) && ((User) other).equalKeys(this);
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
		i = getUserId();
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
		StringBuffer sb = new StringBuffer("[Users |");
		sb.append(" userId=").append(getUserId());
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
		ret.put("userId", Integer.valueOf(getUserId()));
		return ret;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
