/**
 * DATN_FALL2022, 2022
 * ProductVariantRestControllerUser.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.user;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.beans.ProAndOpAndOv;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.service.ProductVariantService;
import com.pro2111.service.SaleService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("user/rest/product-variants")
public class ProductVariantRestControllerUser {

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private SaleService saleService;

	/**
	 * Lấy ProductVariant theo proAndOpAndOv
	 * 
	 * @param proAndOpAndOv
	 * @return
	 */
	@PostMapping("find-by-product-option-optionvalue")
	public ResponseEntity<ProductVariant> findByProductAndOptionAndOptionValue(
			@RequestBody ProAndOpAndOv proAndOpAndOv) {
		try {
			return ResponseEntity.ok(productVariantService.findByProductAndOptionAndOptionValue(proAndOpAndOv));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy khoảng giá ProductVariant theo productId
	 * 
	 * @param product
	 * @return
	 */
	@GetMapping("get-price-range/{idProduct}")
	public ResponseEntity<JsonNode> getPriceRangeByProduct(@PathVariable("idProduct") Product product) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			BigDecimal minPrice = productVariantService.findMinPriceByProduct(product);
			BigDecimal maxPrice = productVariantService.findMaxPriceByProduct(product);

			node.put("price", String.format("%,.0f", minPrice) + "VND - " + String.format("%,.0f", maxPrice) + "VND");
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy khoảng giá min, max ProductVariant
	 * 
	 * @return
	 */
	@GetMapping("/price-ranges")
	public ResponseEntity<Object> getPriceRangeProducts() {
		Object response = ResponseEntity.ok(productVariantService.getPriceRange());
		return ResponseEntity.ok(response);
	}

	/**
	 * Lấy discount của ProductVariant theo variantId
	 * 
	 * @param productVariant
	 * @return
	 */
	@GetMapping("find-discount-sale-by-product-variant/{id}")
	public ResponseEntity<JsonNode> findDiscountSaleByProductVariant(
			@PathVariable("id") ProductVariant productVariant) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			node.put("discount", saleService.findDiscountSaleByProductVariant(productVariant));
			return ResponseEntity.ok(node);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
