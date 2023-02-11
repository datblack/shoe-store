// Generated with g9.

package com.pro2111.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionPK implements Serializable {

	@ManyToOne(optional = false)
	@JoinColumn(name = "Option_id", nullable = false)
	private Option options;

	@ManyToOne(optional = false)
	@JoinColumn(name = "Product_id", nullable = false)
	private Product producs;

}
