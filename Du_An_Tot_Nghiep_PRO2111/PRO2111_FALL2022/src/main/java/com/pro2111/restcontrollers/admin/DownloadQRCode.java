/**
 * DATN_FALL2022, 2022
 * DownloadQRCode.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.utils.Constant;
import com.pro2111.utils.ZXingHelper;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("download-QRCode")
public class DownloadQRCode {

	/**
	 * Tải mã QR của bill theo billId
	 * 
	 * @param billId
	 * @param response
	 * @param request
	 * @return
	 */
	@GetMapping("bill/{billId}")
	public ResponseEntity<?> downloadQrCode(@PathVariable("billId") String billId, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			response.setContentType("application/octet-stream");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String currentDateTime = LocalDateTime.now().format(formatter);
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=" + billId + "_" + currentDateTime + ".png";
			response.setHeader(headerKey, headerValue);
			response.setContentType("image/png");
			ServletOutputStream outputStream = response.getOutputStream();
			StringBuffer url = new StringBuffer();
			url.append(request.getScheme());
			url.append("://");
			url.append(request.getServerName());
			url.append(":");
			url.append(request.getServerPort());
			url.append("/");
			url.append(Constant.NAME_PROJECT);
			url.append(Constant.URI_PDF);
			url.append(billId);
			outputStream.write(ZXingHelper.getQRCodeImage(url.toString(), 100, 100));
			outputStream.flush();
			outputStream.close();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
