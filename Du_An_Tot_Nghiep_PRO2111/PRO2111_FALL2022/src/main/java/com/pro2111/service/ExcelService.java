/**
 * DATN_FALL2022, 2022
 * ExcelService.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface ExcelService {

	/**
	 * @param response
	 */
	public void createResponse(XSSFWorkbook workbook, HttpServletResponse response) throws Exception;

	/**
	 * @param response
	 */
	public void export(XSSFWorkbook workbook, HttpServletResponse response) throws Exception;

}
