// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.pro2111.validations.options.UniqueOptionName;

@Entity
@Table(name = "options")
@Indexed
public class Option implements Serializable {

	/** Primary key. */
	protected static final String PK = "optionId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Option_id", unique = true, nullable = false, precision = 10)
	private int optionId;

	@NotBlank(message = "{Option.optionName.NotBlank}")
	@UniqueOptionName(message = "{Option.optionName.UniqueOptionName}")
	@Length(min = 3, max = 100, message = "{Option.optionName.Length}")
	@Field(termVector = TermVector.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "Option_name", nullable = false, length = 100)
	private String optionName;

	@NotNull(message = "{Option.status.NotNull}")
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "Status", nullable = false, precision = 10)
	private int status;

	@Column(name = "is_show", nullable = false, precision = 10)
	private int isShow;

	@JsonIgnore
	@OneToMany(mappedBy = "options")
	private List<ProductOption> productOptions;

	@JsonIgnore
	@OneToMany(mappedBy = "options")
	private Set<OptionValue> optionValues;

	/** Default constructor. */
	public Option() {
		super();
	}

	/**
	 * Access method for optionId.
	 *
	 * @return the current value of optionId
	 */
	public int getOptionId() {
		return optionId;
	}

	/**
	 * Setter method for optionId.
	 *
	 * @param aOptionId the new value for optionId
	 */
	public void setOptionId(int aOptionId) {
		optionId = aOptionId;
	}

	/**
	 * Access method for optionName.
	 *
	 * @return the current value of optionName
	 */
	public String getOptionName() {
		return optionName;
	}

	/**
	 * Setter method for optionName.
	 *
	 * @param aOptionName the new value for optionName
	 */
	public void setOptionName(String aOptionName) {
		optionName = FormateString.UpperCaseString(aOptionName);
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
	 * Access method for productOptions.
	 *
	 * @return the current value of productOptions
	 */
	public List<ProductOption> getProductOptions() {
		return productOptions;
	}

	/**
	 * Setter method for productOptions.
	 *
	 * @param aProductOptions the new value for productOptions
	 */
	public void setProductOptions(List<ProductOption> aProductOptions) {
		productOptions = aProductOptions;
	}

	/**
	 * Access method for optionValues.
	 *
	 * @return the current value of optionValues
	 */
	public Set<OptionValue> getOptionValues() {
		return optionValues;
	}

	/**
	 * Setter method for optionValues.
	 *
	 * @param aOptionValues the new value for optionValues
	 */
	public void setOptionValues(Set<OptionValue> aOptionValues) {
		optionValues = aOptionValues;
	}

	/**
	 * Compares the key for this instance with another Options.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Options and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Option)) {
			return false;
		}
		Option that = (Option) other;
		if (this.getOptionId() != that.getOptionId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Options.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Option))
			return false;
		return this.equalKeys(other) && ((Option) other).equalKeys(this);
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
		i = getOptionId();
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
		StringBuffer sb = new StringBuffer("[Options |");
		sb.append(" optionId=").append(getOptionId());
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
		ret.put("optionId", Integer.valueOf(getOptionId()));
		return ret;
	}

}
