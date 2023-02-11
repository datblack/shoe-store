package com.pro2111.payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/vnpay")
public class Controller {

	@PostMapping("/send")
	public ResponseEntity<JsonNode> PostAPI(HttpServletRequest req,
			@RequestParam(name = "vnp_OrderInfo") String vnp_OrderInfo,
			@RequestParam(name = "ordertype") String orderType, @RequestParam(name = "amount") String amoutParam,
			@RequestParam(name = "bankcode") String bank_code, @RequestParam(name = "language") String locate)
			throws UnsupportedEncodingException {
		ObjectMapper mapper;
		ObjectNode node = null;
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";

		String vnp_TxnRef = Config.getRandomNumber(8);
		String vnp_IpAddr = Config.getIpAddress(req);
		String vnp_TmnCode = Config.vnp_TmnCode;

		int amount = Integer.parseInt(amoutParam) * 100;//
		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		if (bank_code != null && !bank_code.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bank_code);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", orderType);

		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());

		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		// Add Params of 2.0.1 Version
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
		// Build data to hash and querystring
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator iterator = fieldNames.iterator();
		while (iterator.hasNext()) {
			String fieldName = (String) iterator.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
				if (iterator.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		System.out.println(hashData.toString());

		mapper = new ObjectMapper();
		node = mapper.createObjectNode();
		node.put("value", Config.vnp_PayUrl + "?" + queryUrl);
		return ResponseEntity.ok(node);
	}

	@PostMapping("user/send")
	public ResponseEntity<JsonNode> customerPostAPI(HttpServletRequest req,
			@RequestParam(name = "vnp_OrderInfo") String vnp_OrderInfo,
			@RequestParam(name = "ordertype") String orderType, @RequestParam(name = "amount") String amoutParam,
			@RequestParam(name = "bankcode") String bank_code, @RequestParam(name = "language") String locate)
			throws UnsupportedEncodingException {
		ObjectMapper mapper;
		ObjectNode node = null;
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";

		String vnp_TxnRef = Config.getRandomNumber(8);
		String vnp_IpAddr = Config.getIpAddress(req);
		String vnp_TmnCode = Config.vnp_TmnCode;

		int amount = Integer.parseInt(amoutParam) * 100;//
		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");
		if (bank_code != null && !bank_code.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bank_code);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", orderType);

		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnurlCustomer);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());

		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		// Add Params of 2.0.1 Version
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
		// Build data to hash and querystring
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator iterator = fieldNames.iterator();
		while (iterator.hasNext()) {
			String fieldName = (String) iterator.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
				if (iterator.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		System.out.println(hashData.toString());

		mapper = new ObjectMapper();
		node = mapper.createObjectNode();
		node.put("value", Config.vnp_PayUrl + "?" + queryUrl);
		return ResponseEntity.ok(node);
	}
}
