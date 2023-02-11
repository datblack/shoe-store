/**
 * DATN_FALL2022, 2022
 * UpdatePriceProductVariantRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.ProductVariansBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.ProductService;
import com.pro2111.service.ProductVariantService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/update-price-product-variant")
public class UpdatePriceProductVariantRestControllerAdmin {
	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private ProductService productService;

	@Autowired
	private VariantValueRepository variantValueRepository;

	public ProductVariansBean beanPublic;

	/**
	 * Lấy tất cả ProductVariant
	 */
	@GetMapping("get-all-product-variant")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> getAllProductVariant() {
		try {
			List<ProductVariant> productVariants = productVariantService.findAll();
			productVariants.forEach(pv -> {
				try {
					pv = customNameProductByOneProductVariant(pv);
				} catch (ReflectiveOperationException e) {
					e.printStackTrace();
				}
			});
			return ResponseEntity.ok(productVariants);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Gửi data vào beanPublic
	 * 
	 * @param bean
	 */
	@PostMapping("post-data")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public void postData(@RequestBody ProductVariansBean bean) {
		beanPublic = bean;
	}

	/**
	 * Cập nhật giá nhanh
	 * 
	 * @param bean
	 * @return
	 */
	@PutMapping("update-price")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<ProductVariansBean> updatePrice(@Valid @RequestBody ProductVariansBean bean) {
		beanPublic = bean;
		try {
			return ResponseEntity.ok(productVariantService.updatePrice(bean));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			int index = Integer.parseInt(fieldName.charAt(Constant.INDEX_NAME_VALIDATE) + "");
			ProductVariant pvNew = beanPublic.getProductVariants().get(index);

			int indexNew = beanPublic.getProductVariantsOld().indexOf(pvNew);
			fieldName = fieldName.substring(0, Constant.INDEX_NAME_VALIDATE) + indexNew
					+ fieldName.substring(Constant.INDEX_NAME_VALIDATE + 1);
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	/**
	 * CustomeName
	 * 
	 * @param productVariant
	 * @return
	 * @throws ReflectiveOperationException
	 */
	public ProductVariant customNameProductByOneProductVariant(ProductVariant productVariant)
			throws ReflectiveOperationException {
		Product productOld = productService.findById(productVariant.getProducts().getProductId());
		List<VariantValue> list = variantValueRepository.findByProductVariantsLike(productVariant);
		Comparator<VariantValue> comparator = Comparator.comparing(h -> h.getOptionValues().getValueName());
		list.sort(comparator);
		StringJoiner name = new StringJoiner("-");
		for (int i = 0; i < list.size(); i++) {
			VariantValue value = list.get(i);
			if (value.getOptionValues().getIsShow() == 1) {
				name.add(value.getOptionValues().getValueName());
			}
		}
		String productName = " [" + name.toString() + "]";
		Product productNew = new Product();
		BeanUtils.copyProperties(productOld, productNew);
		productNew.setProductName(productOld.getProductName() + productName);
		productVariant.setProducts(productNew);

		return productVariant;
	}
}
