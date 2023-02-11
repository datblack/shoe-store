/**
 * DATN_FALL2022, 2022
 * ExcelServiceImpl.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.entities.Option;
import com.pro2111.entities.Product;
import com.pro2111.repositories.ProductRepository;
import com.pro2111.service.ExcelService;
import com.pro2111.service.OptionService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	public OptionService optionService;

	/**
	 * Tạo file Excel
	 */
	@Override
	public void createResponse(XSSFWorkbook workbook, HttpServletResponse response) throws Exception {
		try {
			// configuration export
			response.setContentType("application/octet-stream");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormat.format(new Date());
			String headerKey = "Content-Disposition";
			String headerValue = "attchment; filename=" + Constant.NAME_FILE_EXCEL_PRODUCT_VARIANT + "_"
					+ currentDateTime + ".xlsx";
			response.setHeader(headerKey, headerValue);
			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * Xuất fileExcel
	 */
	@Override
	public void export(XSSFWorkbook workbook, HttpServletResponse response) throws Exception {
		exportProductVariant(workbook, response);
	}

	/**
	 * Xuất list ProductVariant
	 * 
	 * @param workbook
	 * @param response
	 * @throws Exception
	 */
	public void exportProductVariant(XSSFWorkbook workbook, HttpServletResponse response) throws Exception {
		try {
			CellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false);
			XSSFSheet spreadsheet = workbook.createSheet("Sản phẩm chi tiết");

			// Khóa sheet
//			spreadsheet.protectSheet("Hieu113.");

			Row row = null;
			Cell cell = null;

			row = spreadsheet.createRow((short) 0);
			row.setHeight((short) 500);
			cell = row.createCell(0, CellType.STRING);
			cell.setCellValue("DANH SÁCH SẢN PHẨM CHI TIẾT");
			// merge cell
			int firstRowMerge = 0;
			int lastRowMerge = 0;
			int firstColMerge = 0;
			int lastColMerge = 5;
			spreadsheet.addMergedRegion(new CellRangeAddress(firstRowMerge, lastRowMerge, firstColMerge, lastColMerge));

			row = spreadsheet.createRow((short) 1);
			row.setHeight((short) 500);

			cell = row.createCell(0, CellType.STRING);
			cell.setCellValue("Sản phẩm");

			cell = row.createCell(1, CellType.STRING);
			cell.setCellValue("Số lượng");

			cell = row.createCell(2, CellType.STRING);
			cell.setCellValue("Thuế (%)");

			cell = row.createCell(3, CellType.STRING);
			cell.setCellValue("Giá nhập (VNĐ)");

			cell = row.createCell(4, CellType.STRING);
			cell.setCellValue("Giá xuất (VNĐ)");

			cell = row.createCell(5, CellType.STRING);
			cell.setCellValue("Thuộc tính sản phẩm");

			for (int i = 0; i < 6; i++) {
				spreadsheet.autoSizeColumn(i);
			}
//			spreadsheet.setColumnWidth(0, 8000);
//			spreadsheet.setColumnWidth(1, 4000);
//			spreadsheet.setColumnWidth(2, 4000);
//			spreadsheet.setColumnWidth(3, 6000);
//			spreadsheet.setColumnWidth(4, 6000);
//			spreadsheet.setColumnWidth(5, 20000);
			// Mở khóa các ô cần điền dữ liệu
//			for (int i = 2; i < 1003; i++) {
//				row = spreadsheet.createRow((short) i);
//				cell = row.createCell(0);
//				cell.setCellStyle(unlockedCellStyle);
//				//
//				cell = row.createCell(1);
//				cell.setCellStyle(unlockedCellStyle);
//				//
//				cell = row.createCell(2);
//				cell.setCellStyle(unlockedCellStyle);
//				//
//				cell = row.createCell(3);
//				cell.setCellStyle(unlockedCellStyle);
//				//
//				cell = row.createCell(4);
//				cell.setCellStyle(unlockedCellStyle);
//				//
//				cell = row.createCell(5);
//				cell.setCellStyle(unlockedCellStyle);
//			}

			// Tạo dropdown sản phẩm
			createDropdownListProduct(spreadsheet);
			// Tạo dropdown option
			createDropdownListOption(spreadsheet);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Tạo DropdownListProduct
	 * 
	 * @param spreadsheet
	 */
	public void createDropdownListProduct(XSSFSheet spreadsheet) {
		List<Product> products = productRepository.findByStatusLike(Constant.PRODUCT_STATUS_TRUE);

		DataValidation dataValidation = null;
		DataValidationConstraint constraint = null;
		DataValidationHelper validationHelper = null;
		String[] arrayString = new String[products.size()];
		for (int i = 0; i < products.size(); i++) {
			arrayString[i] = products.get(i).getProductName();
		}

		validationHelper = new XSSFDataValidationHelper(spreadsheet);
		CellRangeAddressList listProduct = new CellRangeAddressList(2, 1002, 0, 0);
		constraint = validationHelper.createExplicitListConstraint(arrayString);
		dataValidation = validationHelper.createValidation(constraint, listProduct);
		dataValidation.setSuppressDropDownArrow(true);
		spreadsheet.addValidationData(dataValidation);
	}

	/**
	 * Tạo DropdownListOption
	 * 
	 * @param spreadsheet
	 */
	public void createDropdownListOption(XSSFSheet spreadsheet) {
		List<String> list = new ArrayList<>();
		List<Option> options = optionService.findByStatusLike(Constant.OPTION_STATUS_TRUE);
		options.forEach(o -> {
			o.getOptionValues().forEach(ov -> {
				if (ov.getStatus() == Constant.OPTION_VALUE_STATUS_TRUE) {
					StringBuffer customName = new StringBuffer();
					customName.append(o.getOptionName());
					customName.append(":");
					customName.append(ov.getValueName());
					list.add(customName.toString());
				}
			});
		});

		DataValidation dataValidation = null;
		DataValidationConstraint constraint = null;
		DataValidationHelper validationHelper = null;
		String[] arrayString = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			arrayString[i] = list.get(i);

		validationHelper = new XSSFDataValidationHelper(spreadsheet);
		CellRangeAddressList listProduct = new CellRangeAddressList(2, 1002, 5, 5);
		constraint = validationHelper.createExplicitListConstraint(arrayString);
		dataValidation = validationHelper.createValidation(constraint, listProduct);
		dataValidation.setSuppressDropDownArrow(true);
		spreadsheet.addValidationData(dataValidation);
	}

}
