/**
 * DATN_FALL2022, 2022
 * Common.java, BUI_QUANG_HIEU
 */
package com.pro2111.utils;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.SettingService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class Common {

	@Autowired
	static SettingService settingService;

	@Autowired
	static VariantValueRepository variantValueRepository;

	public static Integer convertStringToInt(String input, Integer defaultValue) {
		Integer value = 0;
		try {
			value = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			value = defaultValue;
		}
		return value;
	}

	public static Boolean checkStartDateSale(LocalDateTime dateTime) {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondInput = dateTime.toEpochSecond(ZoneOffset.UTC);
		long secondNow = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		long minute = (secondNow - secondInput) / 60;
		if (minute <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean checkStartDateSaleCreate(LocalDateTime dateTime) {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondInput = dateTime.toEpochSecond(ZoneOffset.UTC);
		long secondNow = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		long minute = (secondNow - secondInput) / 60;
		if (minute < 0) {
			return false;
		} else {
			return true;
		}
	}

	public static Boolean checkBigDecimal(String input) {
		Boolean check = false;
		try {
			new BigDecimal(input);
			check = true;
		} catch (Exception e) {
			check = false;
		}
		return check;
	}

	public static List<ProductVariant> customNameProductByProductVariant(List<ProductVariant> productVariantsReturn) {
		List<String> productName = new ArrayList<String>();
		for (int i = 0; i < productVariantsReturn.size(); i++) {
			productName.add(productVariantsReturn.get(i).getProducts().getProductName());
		}

		for (int i = 0; i < productVariantsReturn.size(); i++) {
			ProductVariant pv = productVariantsReturn.get(i);
			List<VariantValue> list = new ArrayList<>(pv.getVariantValues());
			Comparator<VariantValue> comparator = Comparator.comparing(h -> h.getOptionValues().getValueName());
			list.sort(comparator);
			StringJoiner name = new StringJoiner("-");
			for (int j = 0; j < list.size(); j++) {
				VariantValue value = list.get(j);
				if (value.getOptionValues().getIsShow() == 1) {
					name.add(value.getOptionValues().getValueName());
				}
			}
			Product product = new Product();
			product.setProductName(productName.get(i) + " [" + name.toString() + "]");
			pv.setProducts(product);
		}
		return productVariantsReturn;
	}

	public static Boolean checkEndDateSale(LocalDateTime start, LocalDateTime end) {

		long secondStart = start.toEpochSecond(ZoneOffset.UTC);
		long secondEnd = end.toEpochSecond(ZoneOffset.UTC);
		long minute = (secondEnd - secondStart) / 60;
		if (minute < 5) {
			return false;
		} else {
			return true;
		}
	}

	// check created user
	public static Boolean checkCreatedDateUser(LocalDateTime createDate) {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondStart = createDate.toEpochSecond(ZoneOffset.UTC);
		long secondEnd = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		long second = secondEnd - secondStart;
		if (second < 120) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean checkEndDateSaleChild(LocalDateTime start, LocalDateTime end) {

		long secondStart = start.toEpochSecond(ZoneOffset.UTC);
		long secondEnd = end.toEpochSecond(ZoneOffset.UTC);
		long day = (secondEnd - secondStart) / 60 / 60 / 24;
		if (day < 7) {
			return false;
		} else {
			return true;
		}
	}

	// check startAt and endAt
	public static Boolean checkEndAtSaleChild(LocalTime start, LocalTime end) {

		long secondStart = start.toSecondOfDay();
		long secondEnd = end.toSecondOfDay();
		long minute = (secondEnd - secondStart) / 60;
		if (minute < 5) {
			return false;
		} else {
			return true;
		}
	}

	// check startAt and endAt to Local
	public static Boolean checkTimeToLocal(LocalTime inPut) {

		long secondStart = inPut.toSecondOfDay();
		long secondEnd = LocalTime.now().toSecondOfDay();
		long minute = (secondEnd - secondStart) / 60;
		if (minute <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public static String genarateSkuID(List<String> options, List<String> optionValues) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SKU-");
		for (int i = 0; i < options.size(); i++) {
			String option = removeAccentsText(options.get(i));
			String optionValue = removeAccentsText(optionValues.get(i));
			if (option.length() < 3) {
				buffer.append(option);
			} else if (option.length() >= 3) {
				buffer.append(option.substring(0, 3));
			}
			if (optionValue.length() < 3) {
				buffer.append(optionValue);
			} else if (optionValue.length() >= 3) {
				buffer.append(optionValue.substring(0, 3));
			}
		}
		String rusult = buffer.toString();
		rusult = rusult.replace(" ", "");
		return rusult;
	}

	public static void main(String[] args) {

//		Gson gson = new Gson();
//		BankBean bankBean = new BankBean("83838383456789", "MBBank", "BUI QUANG HIEU");
//		System.out.println(gson.toJson(bankBean));
//
//		// Recipient's email ID needs to be mentioned.
//        String to = "bui.quang.hieu2910@gmail.com";
//
//        // Sender's email ID needs to be mentioned
//        String from = "horsesoftware002@gmail.com";
//
//        // Assuming you are sending email from through gmails smtp
//        String host = "smtp.gmail.com";
//
//        // Get system properties
//        Properties properties = System.getProperties();
//
//        // Setup mail server
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//
//        // Get the Session object.// and pass username and password
//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//
//            protected PasswordAuthentication getPasswordAuthentication() {
//
//                return new PasswordAuthentication("horsesoftware002@gmail.com", "pzssisrjqvnslsxn");
//
//            }
//
//        });
//
//        // Used to debug SMTP issues
//        session.setDebug(true);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(from));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject: header field
//            message.setSubject("This is the Subject Line!");
//
//            // Now set the actual message
//            message.setText("This is actual message");
//
//            System.out.println("sending...");
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }

		String phoneString = "039596200220022002121";
		Pattern pattern = Pattern.compile("[0-9]{10,20}$");
		Matcher matcher = pattern.matcher(phoneString);
		System.out.println(matcher.matches());

	}

	public static String removeAccentsText(String s) {
		s.trim();
		s = s.toUpperCase();
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		temp = pattern.matcher(temp).replaceAll("");
		return temp.replaceAll("đ", "d");
	}

	// Check trùng saleChile
	public static Boolean checkSaleChild(Sale saleChid, List<Sale> listSaleChild) {
		listSaleChild.remove(saleChid);
		Boolean check = false;

		return check;
	}

	// Convert LocalDate to Date
	public static Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

}
