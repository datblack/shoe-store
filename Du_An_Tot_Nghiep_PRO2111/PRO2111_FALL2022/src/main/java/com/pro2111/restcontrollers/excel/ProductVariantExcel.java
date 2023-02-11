/**
 * DATN_FALL2022, 2022
 * ProductVariantExcel.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.excel;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.service.ExcelService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/excel")
public class ProductVariantExcel {
	@Autowired
	private ExcelService excelService;

	@GetMapping("export-template")
	public void export(HttpServletResponse response) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		excelService.export(workbook, response);
		excelService.createResponse(workbook, response);
	}

}
