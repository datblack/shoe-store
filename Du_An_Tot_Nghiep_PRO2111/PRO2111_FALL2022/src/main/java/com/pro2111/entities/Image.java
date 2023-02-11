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

@Entity
@Table(name = "images")
public class Image implements Serializable {

	/** Primary key. */
//	protected static final String PK = "imagesId";

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "images_id", unique = true, nullable = false, length = 50)
	private String imagesId;
	@Column(name = "image_path", nullable = false, length = 255)
	private String imagePath;
	@Column(name = "status", nullable = false, precision = 10)
	private int status;
	@ManyToOne
	@JoinColumn(name = "product_variant_id")
	private ProductVariant productVariants;

	/** Default constructor. */
	public Image() {
		super();
	}

	/**
	 * Access method for imagesId.
	 *
	 * @return the current value of imagesId
	 */
	public String getImagesId() {
		return imagesId;
	}

	/**
	 * Setter method for imagesId.
	 *
	 * @param aImagesId the new value for imagesId
	 */
	public void setImagesId(String aImagesId) {
		imagesId = aImagesId;
	}

	/**
	 * Access method for imagePath.
	 *
	 * @return the current value of imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Setter method for imagePath.
	 *
	 * @param aImagePath the new value for imagePath
	 */
	public void setImagePath(String aImagePath) {
		imagePath = aImagePath;
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
	 * Compares the key for this instance with another Images.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Images and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Image)) {
			return false;
		}
		Image that = (Image) other;
		if (this.getImagesId() != that.getImagesId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Images.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Image))
			return false;
		return this.equalKeys(other) && ((Image) other).equalKeys(this);
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
		i = getImagesId().hashCode();
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
		StringBuffer sb = new StringBuffer("[Images |");
		sb.append(" imagesId=").append(getImagesId());
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
		ret.put("imagesId", getImagesId());
		return ret;
	}

}
