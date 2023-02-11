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
import com.pro2111.validations.optionValues.UniqueOptionValueName;

@Entity
@Table(name = "option_values")
@Indexed
public class OptionValue implements Serializable {

	/** Primary key. */
	protected static final String PK = "valueId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Value_id", unique = true, nullable = false, precision = 10)
	private int valueId;

	@NotBlank(message = "{OptionValue.valueName.NotBlank}")
	@Length(min = 1, max = 100,message = "{OptionValue.valueName.Length}")
	@UniqueOptionValueName(message = "{OptionValue.valueName.UniqueOptionValueName}")
	@Field(termVector = TermVector.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "Value_name", nullable = false, length = 100)
	private String valueName;

	@NotNull(message = "{OptionValue.status.NotNull}")
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "Status", nullable = false, precision = 10)
	private int status;

	@Column(name = "is_show", nullable = false, precision = 10)
	private int isShow;

	@NotNull(message = "{OptionValue.options.NotNull}")
	@ManyToOne(optional = false)
	@JoinColumn(name = "Option_id", nullable = false)
	private Option options;

	@JsonIgnore
	@OneToMany(mappedBy = "optionValues")
	private Set<VariantValue> variantValues;

	/** Default constructor. */
	public OptionValue() {
		super();
	}

	/**
	 * Access method for valueId.
	 *
	 * @return the current value of valueId
	 */
	public int getValueId() {
		return valueId;
	}

	/**
	 * Setter method for valueId.
	 *
	 * @param aValueId the new value for valueId
	 */
	public void setValueId(int aValueId) {
		valueId = aValueId;
	}

	/**
	 * Access method for valueName.
	 *
	 * @return the current value of valueName
	 */
	public String getValueName() {
		return valueName;
	}

	/**
	 * Setter method for valueName.
	 *
	 * @param aValueName the new value for valueName
	 */
	public void setValueName(String aValueName) {
		valueName = FormateString.UpperCaseString(aValueName);
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
	 * @return the isShow
	 */
	public int getIsShow() {
		return isShow;
	}

	/**
	 * @param isShow the isShow to set
	 */
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	/**
	 * @return the pk
	 */
	public static String getPk() {
		return PK;
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

	/**
	 * Compares the key for this instance with another OptionValues.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class OptionValues and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OptionValue)) {
			return false;
		}
		OptionValue that = (OptionValue) other;
		if (this.getValueId() != that.getValueId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another OptionValues.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof OptionValue))
			return false;
		return this.equalKeys(other) && ((OptionValue) other).equalKeys(this);
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
		i = getValueId();
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
		StringBuffer sb = new StringBuffer("[OptionValues |");
		sb.append(" valueId=").append(getValueId());
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
		ret.put("valueId", Integer.valueOf(getValueId()));
		return ret;
	}

}
