/**
 * DATN_FALL2022, 2022
 * GenarateQRCodeRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.utils.ZXingHelper;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/genarate-QRCode")
public class GenarateQRCodeRestControllerAdmin {

	/**
	 * Tạo mã QR cho product variant theo Id
	 * 
	 * @param productVariant
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@GetMapping("product-variant/{variantId}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public void index(@PathVariable("variantId") ProductVariant productVariant, HttpServletResponse response,
			Model model) throws IOException {

		List<VariantValue> list = new ArrayList<>(productVariant.getVariantValues());
		Comparator<VariantValue> comparator = Comparator.comparing(h -> h.getOptionValues().getValueName());
		list.sort(comparator.reversed());
		StringJoiner name = new StringJoiner("-");
		for (int j = 0; j < list.size(); j++) {
			VariantValue value = list.get(j);
			if (value.getOptionValues().getIsShow() == 1) {
				name.add(value.getOptionValues().getValueName());
			}
		}
		Product product = new Product();
		product.setProductName(productVariant.getProducts().getProductName() + " [" + name.toString() + "]"
				+ productVariant.getVariantId());
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(ZXingHelper.getQRCodeImage(product.getProductName(), 100, 100));
		outputStream.flush();
		outputStream.close();
	}

}
