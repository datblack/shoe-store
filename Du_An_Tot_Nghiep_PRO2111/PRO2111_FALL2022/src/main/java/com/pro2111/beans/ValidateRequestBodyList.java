/**
 * DATN_FALL2022, 2022
 * ValidateRequestBodyList.java, BUI_QUANG_HIEU
 */
package com.pro2111.beans;

import java.util.List;

import javax.validation.Valid;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class ValidateRequestBodyList<T> {
	@Valid
	private List<T> requestBody;

	public List<T> getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(List<T> requestBody) {
		this.requestBody = requestBody;
	}

	public ValidateRequestBodyList(List<T> requestBody) {
		this.requestBody = requestBody;
	}

	public ValidateRequestBodyList() {
	}
}
