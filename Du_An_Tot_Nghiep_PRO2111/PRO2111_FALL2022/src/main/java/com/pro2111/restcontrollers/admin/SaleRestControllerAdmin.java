package com.pro2111.restcontrollers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pro2111.beans.SaleAndProductVariants;
import com.pro2111.beans.SaleAndSaleChild;
import com.pro2111.beans.VariantValueViewSaleBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.User;
import com.pro2111.i18n.ExposedResourceBundleMessageSource;
import com.pro2111.service.SaleService;
import com.pro2111.service.UserService;
import com.pro2111.utils.Common;
import com.pro2111.utils.Constant;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/sales")
public class SaleRestControllerAdmin {

	@Autowired
	private SaleService saleService;

	@Autowired
	private UserService userService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	ExposedResourceBundleMessageSource bundleMessageSource;

	static final int MINUTES_PER_HOUR = 60;
	static final int SECONDS_PER_MINUTES = 60;
	static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTES * MINUTES_PER_HOUR;

	public Map<String, String> getListError(Locale locale) {
		Map<String, String> map = bundleMessageSource.getValueContaisKey(Constant.PATH_PROPERTIES_I18N, locale, "Sale");
		return map;
	}

	/**
	 * Lấy tất cả sale
	 * 
	 * @param statuString
	 * @return
	 */
	@GetMapping
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<Sale>> finAll() {
		try {
			return ResponseEntity.ok(saleService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy sale theo id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<SaleAndProductVariants> findById(@PathVariable("idSale") String id) {
		try {

			return ResponseEntity.ok(saleService.findSaleAndProductById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Thêm mới saleAndProduct
	 * 
	 * @param saleAndProduct
	 * @return
	 */
	@PostMapping()
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> createSaleAndProduct(@Valid @RequestBody SaleAndProductVariants saleAndProduct,
			Locale locale) {
		try {
			Map<String, String> listErrorMap = getListError(locale);
			Map<String, String> errorsMap = new HashMap<>();
			// Validate saleName
			if (saleAndProduct.getSale().getSaleName().isBlank()) {
				errorsMap.put("saleName", listErrorMap.get("Sale.saleName.NotBlank"));
			} else {
				if (!saleService.checkSaleName(saleAndProduct.getSale(), null, request.getMethod())) {
					errorsMap.put("saleName", listErrorMap.get("Sale.saleName.Unique"));
				}
			}
			// Validate discount
			if (saleAndProduct.getSale().getDiscount() == null) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.NotNull"));
			} else if (!Common.checkBigDecimal(saleAndProduct.getSale().getDiscount().toString())) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.NotNumber"));
			} else if (saleAndProduct.getSale().getDiscountType() == 0
					&& saleAndProduct.getSale().getDiscount().doubleValue() < 10000) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.ValidateMoney"));

			} else if (saleAndProduct.getSale().getDiscountType() == 1
					&& ((saleAndProduct.getSale().getDiscount().doubleValue() > 100
							|| saleAndProduct.getSale().getDiscount().doubleValue() <= 0))) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.ValidateRate"));
			}

			// Validate startDate
			if (saleAndProduct.getSale().getStartDate() == null) {
				errorsMap.put("startDate", listErrorMap.get("Sale.startDate.NotNull"));
			} else {
				if (!Common.checkStartDateSale(saleAndProduct.getSale().getStartDate())) {
					errorsMap.put("startDate", listErrorMap.get("Sale.startDate.Validate"));
				}
			}

			// Validate endDate
			if (saleAndProduct.getSale().getEndDate() == null) {
				errorsMap.put("endDate", listErrorMap.get("Sale.endDate.NotNull"));
			} else if (saleAndProduct.getSale().getStartDate() != null) {
				if (!Common.checkEndDateSale(saleAndProduct.getSale().getStartDate(),
						saleAndProduct.getSale().getEndDate())) {
					errorsMap.put("endDate", listErrorMap.get("Sale.endDate.Validate"));
				}
			}

			// Validate listProductVariant
			if (saleAndProduct.getListProductVariants() == null
					|| saleAndProduct.getListProductVariants().size() == 0) {
				errorsMap.put("listProductVariants", listErrorMap.get("Sale.listProductVariant.NotEmpty"));
			}

			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				return ResponseEntity.ok().body(saleService.create(saleAndProduct));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	/**
	 * cập nhật saleAndProduct
	 * 
	 * @param idSale, saleAndProduct
	 * @return
	 */
	@PutMapping("{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> updateSaleAndProduct(@Valid @RequestBody SaleAndProductVariants saleAndProduct,
			@PathVariable("idSale") String id, @AuthenticationPrincipal UserDetails userDetails, Locale locale) {
		try {
			Sale saleOld = saleService.findSaleById(id);
			Map<String, String> listErrorMap = getListError(locale);
			Map<String, String> errorsMap = new HashMap<>();
			// Validate saleName
			if (saleAndProduct.getSale().getSaleName().isBlank()) {
				errorsMap.put("saleName", listErrorMap.get("Sale.saleName.NotBlank"));
			} else {
				if (!saleService.checkSaleName(saleAndProduct.getSale(), saleOld, request.getMethod())) {
					errorsMap.put("saleName", listErrorMap.get("Sale.saleName.Unique"));
				}
			}
			// Validate discount
			if (saleAndProduct.getSale().getDiscount() == null) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.NotNull"));
			} else if (!Common.checkBigDecimal(saleAndProduct.getSale().getDiscount().toString())) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.NotNumber"));
			} else if (saleAndProduct.getSale().getDiscountType() == 0
					&& saleAndProduct.getSale().getDiscount().doubleValue() < 10000) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.ValidateMoney"));

			} else if (saleAndProduct.getSale().getDiscountType() == 1
					&& ((saleAndProduct.getSale().getDiscount().doubleValue() > 100
							|| saleAndProduct.getSale().getDiscount().doubleValue() <= 0))) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.ValidateRate"));
			}

			// Valid saleType = 0
			if (saleAndProduct.getSale().getSaleType() == 0) {
				// Validate startDate
				if (saleAndProduct.getSale().getStartDate() == null) {
					errorsMap.put("startDate", listErrorMap.get("Sale.startDate.NotNull"));
				} else {
					if (!saleOld.getStartDate().equals(saleAndProduct.getSale().getStartDate())) {
						if (!Common.checkStartDateSale(saleAndProduct.getSale().getStartDate())) {
							errorsMap.put("startDate", listErrorMap.get("Sale.startDate.Validate"));
						}
					}
				}
				// Validate endDate
				if (saleAndProduct.getSale().getEndDate() == null) {
					errorsMap.put("endDate", listErrorMap.get("Sale.endDate.NotNull"));
				} else if (saleAndProduct.getSale().getStartDate() != null) {
					if (!saleOld.getEndDate().equals(saleAndProduct.getSale().getEndDate())
							&& !saleOld.getStartDate().equals(saleAndProduct.getSale().getStartDate())) {
						if (!Common.checkEndDateSale(saleOld.getStartDate(), saleAndProduct.getSale().getEndDate())) {
							errorsMap.put("endDate", listErrorMap.get("Sale.endDate.Validate"));
						}
					}
				}
			}

			// Validate listProductVariant
			if (saleAndProduct.getListProductVariants() == null
					|| saleAndProduct.getListProductVariants().size() == 0) {
				errorsMap.put("listProductVariants", listErrorMap.get("Sale.listProductVariant.NotEmpty"));
			}

			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				User userLogin = userService.findByEmail(userDetails.getUsername());
				return ResponseEntity.ok().body(saleService.update(saleAndProduct, userLogin));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ProductVariant được phép khuyến mãi
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("find-product-variants-promotion-is-allowed")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<ProductVariant>> findProductVariantsPromotionIsAllowed(
			@RequestBody List<Product> listProduct) {
		try {
			return ResponseEntity.ok(saleService.findProductVariantsPromotionIsAllowed(listProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy discount theo product-variant
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("find-discount-sale-by-product-variant/{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
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

	/**
	 * Lấy variant-value theo product-variant
	 * 
	 * @param idProductVariant
	 * @return
	 */
	@GetMapping("find-variant-value-by-product-variant/{idProductVariant}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<VariantValueViewSaleBean> findByProductVariant(
			@PathVariable("idProductVariant") ProductVariant productVariant) {
		try {
			return ResponseEntity.ok(saleService.findVariantValueByProductVariant(productVariant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Thêm mớicreateSaleAndSaleChild
	 * 
	 * @param saleAndSaleChild
	 * @return
	 */
	@PostMapping("saleparent-and-salechild")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> createSaleAndSaleChild(@RequestBody SaleAndSaleChild saleAndSaleChild,
			@AuthenticationPrincipal UserDetails userDetails, Locale locale) {
		try {
			System.out.println(userDetails.getUsername());
			Map<String, String> listErrorMap = getListError(locale);
			Map<String, String> errorsMap = new HashMap<>();
			// Validate name
			if (saleAndSaleChild.getSaleParent().getSaleName().isBlank()) {
				errorsMap.put("saleName", listErrorMap.get("Sale.saleName.NotBlank"));
			} else {
				if (!saleService.checkSaleName(saleAndSaleChild.getSaleParent(), null, request.getMethod())) {
					errorsMap.put("saleName", listErrorMap.get("Sale.saleName.Unique"));
				}
			}
			// Validate discount
			if (saleAndSaleChild.getSaleParent().getDiscount() == null) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.NotNull"));
			} else if (!Common.checkBigDecimal(saleAndSaleChild.getSaleParent().getDiscount().toString())) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.NotNumber"));
			} else if (saleAndSaleChild.getSaleParent().getDiscountType() == 0
					&& saleAndSaleChild.getSaleParent().getDiscount().doubleValue() < 10000) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.ValidateMoney"));

			} else if (saleAndSaleChild.getSaleParent().getDiscountType() == 1
					&& ((saleAndSaleChild.getSaleParent().getDiscount().doubleValue() > 100
							|| saleAndSaleChild.getSaleParent().getDiscount().doubleValue() <= 0))) {
				errorsMap.put("discount", listErrorMap.get("Sale.discount.ValidateRate"));
			}

			// Validate SaleType = 0
			if (saleAndSaleChild.getSaleParent().getSaleType() == 0) {
				if (saleAndSaleChild.getSaleParent().getStartDate() == null) {
					errorsMap.put("startDate", listErrorMap.get("Sale.startDate.NotNull"));
				} else {
					if (!Common.checkStartDateSale(saleAndSaleChild.getSaleParent().getStartDate())) {
						errorsMap.put("startDate", listErrorMap.get("Sale.startDate.Validate"));
					}
				}

				// Validate endDate
				if (saleAndSaleChild.getSaleParent().getEndDate() == null) {
					errorsMap.put("endDate", listErrorMap.get("Sale.endDate.NotNull"));
				} else if (saleAndSaleChild.getSaleParent().getStartDate() != null) {
					if (!Common.checkEndDateSale(saleAndSaleChild.getSaleParent().getStartDate(),
							saleAndSaleChild.getSaleParent().getEndDate())) {
						errorsMap.put("endDate", listErrorMap.get("Sale.endDate.Validate"));
					}
				}

			}
			// Validate SaleType = 1
			else if (saleAndSaleChild.getSaleParent().getSaleType() == 1) {
				if (saleAndSaleChild.getLstSaleChild().size() == 0 || saleAndSaleChild.getLstSaleChild() == null) {
					errorsMap.put("lstSaleChild", listErrorMap.get("Sale.lstSaleChild.NotEmpty"));
				} else if (saleAndSaleChild.getLstSaleChild().size() > 5) {
					errorsMap.put("lstSaleChild", listErrorMap.get("Sale.lstSaleChild.Max"));
				}
				List<Sale> lstSaleChildList = saleAndSaleChild.getLstSaleChild();
				for (int i = 0; i < lstSaleChildList.size(); i++) {
					// Validate weekDay
					if (lstSaleChildList.get(i) == null) {
						errorsMap.put("weekDay" + (i + 1), listErrorMap.get("Sale.weekDay.NotBank"));
					} else if (lstSaleChildList.get(i).getWeekday() == 0) {
						errorsMap.put("weekDay" + (i + 1), listErrorMap.get("Sale.weekDay.NotBank"));
					} else if (lstSaleChildList.get(i).getWeekday() < 1 || lstSaleChildList.get(i).getWeekday() > 8) {
						errorsMap.put("weekDay" + (i + 1), listErrorMap.get("Sale.weekDay.NotListWeek"));
					}
					// validate startDate and EndDate
					else if (lstSaleChildList.get(i).getStartDate() == null) {
						if (lstSaleChildList.get(i).getEndDate() != null) {
							errorsMap.put("startDate" + (i + 1), listErrorMap.get("Sale.startDate.NotNull"));
						}
					} else if (lstSaleChildList.get(i).getStartDate() != null) {
						if (lstSaleChildList.get(i).getEndDate() == null) {
							errorsMap.put("endDate" + (i + 1), listErrorMap.get("Sale.endDate.NotNull"));
						} else {
							if (!Common.checkEndDateSaleChild(lstSaleChildList.get(i).getStartDate(),
									lstSaleChildList.get(i).getEndDate())) {
								errorsMap.put("endDate" + (i + 1), listErrorMap.get("Sale.endDate.ValidateSaleChild"));
							}
						}
					}
					// Validate StartAt and EndAt
					else if (lstSaleChildList.get(i).getStartAt() == null) {
						if (lstSaleChildList.get(i).getEndAt() != null) {
							errorsMap.put("startAt" + (i + 1), listErrorMap.get("Sale.endAt.NotNull"));
						}
					} else if (lstSaleChildList.get(i).getStartAt() != null) {
						if (lstSaleChildList.get(i).getEndAt() == null) {
							errorsMap.put("startAt" + (i + 1), listErrorMap.get("Sale.startAt.NotNull"));
						}
					} else if (lstSaleChildList.get(i).getStartAt() != null) {
						if (lstSaleChildList.get(i).getEndAt() == null) {
							errorsMap.put("endAt" + (i + 1), listErrorMap.get("Sale.endAt.NotNull"));
						} else {
							if (!Common.checkEndAtSaleChild(lstSaleChildList.get(i).getStartAt(),
									lstSaleChildList.get(i).getEndAt())) {
								errorsMap.put("endAt" + (i + 1), listErrorMap.get("Sale.endAt.ValidateTime"));
							}
						}
					}
					// Validte trùng khung giờ
					else if (lstSaleChildList.size() > 1) {
						for (int j = i + 1; i < lstSaleChildList.size(); j++) {
							if (lstSaleChildList.get(i).getWeekday() == lstSaleChildList.get(j).getWeekday()) {
								if (lstSaleChildList.get(i).getStartAt().isBefore(lstSaleChildList.get(j).getEndAt())
										&& lstSaleChildList.get(i).getEndAt()
												.isAfter(lstSaleChildList.get(j).getStartAt())) {
									errorsMap.put("weekDay" + (i + 1), listErrorMap.get("Sale.weekDay.Unique"));
								}
							}
						}
					}
				}
			}

			// Validate listProductVariant
			if (saleAndSaleChild.getListProductVariants() == null
					|| saleAndSaleChild.getListProductVariants().size() == 0) {
				errorsMap.put("listProductVariants", listErrorMap.get("Sale.listProductVariant.NotEmpty"));
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				User userLogin = userService.findByEmail(userDetails.getUsername());
				return ResponseEntity.ok().body(saleService.createAndSaleChild(saleAndSaleChild, userLogin));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra saleParent
	 * 
	 * @param
	 * @return
	 */
	@GetMapping("saleparent-and-salechild")
	public ResponseEntity<List<Sale>> findAllSaleParent() {
		try {
			return ResponseEntity.ok().body(saleService.findSaleParent());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Lấy ra SaleAndSaleChild
	 * 
	 * @param idSale
	 * @return
	 */
	@GetMapping("saleparent-and-salechild/{idSale}")
	public ResponseEntity<SaleAndSaleChild> getSaleAndSaleChild(@PathVariable("idSale") String id) {
		try {
			return ResponseEntity.ok().body(saleService.findSaleAndSaleChildBySaleParent(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xóa sale child
	 * 
	 * @param idSale
	 * @return
	 */
	@DeleteMapping("saleparent-and-salechild/{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> deleteSaleChild(@PathVariable("idSale") Sale saleChild,
			@AuthenticationPrincipal UserDetails userDetails, Locale locale) {
		try {
			Map<String, String> errorsMap = new HashMap<>();
			List<Sale> listSaleChildList = saleService.findSaleChileBySaleParent(saleChild.getSaleParent());
			if (listSaleChildList.size() == 1) {
				errorsMap.put("saleChildList", "SaleChildList.NotNull");
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				User userLogin = userService.findByEmail(userDetails.getUsername());
				return ResponseEntity.ok().body(saleService.deleteSaleChild(saleChild, userLogin));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Cập nhật sale child
	 * 
	 * @param idSale, saleReturn
	 * @return
	 */
	@PutMapping("edit-sale_child/{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> editSaleChild(@PathVariable("idSale") String saleId, @RequestBody Sale saleReturn,
			@AuthenticationPrincipal UserDetails userDetails, Locale locale) {
		try {
			List<Sale> listSaleChild = saleService.findSaleChileBySaleParent(saleReturn.getSaleParent());
			Map<String, String> listErrorMap = getListError(locale);
			Map<String, String> errorsMap = new HashMap<>();
			if (saleReturn == null || saleReturn.getWeekday() == 0) {
				errorsMap.put("weekDay", listErrorMap.get("Sale.weekDay.NotBank"));
			} else if (saleReturn.getWeekday() < 1 || saleReturn.getWeekday() > 8) {
				errorsMap.put("weekDay", listErrorMap.get("Sale.weekDay.NotListWeek"));
			}
			// validate startDate and EndDate
			else if (saleReturn.getStartDate() == null) {
				if (saleReturn.getEndDate() != null) {
					errorsMap.put("startDate", listErrorMap.get("Sale.startDate.NotNull"));
				}
			} else if (saleReturn.getStartDate() != null) {
				if (saleReturn.getEndDate() == null) {
					errorsMap.put("endDate", listErrorMap.get("Sale.endDate.NotNull"));
				} else {
					if (!Common.checkEndDateSaleChild(saleReturn.getStartDate(), saleReturn.getEndDate())) {
						errorsMap.put("endDate", listErrorMap.get("Sale.endDate.ValidateSaleChild"));
					}
				}
			}
			// Validate StartAt and EndAt
			else if (saleReturn.getStartAt() == null) {
				if (saleReturn.getEndAt() != null) {
					errorsMap.put("startAt", listErrorMap.get("Sale.startAt.NotNull"));
				}
			} else if (saleReturn.getStartAt() != null) {
				if (saleReturn.getEndAt() == null) {
					errorsMap.put("endAt", listErrorMap.get("Sale.endAt.NotNull"));
				} else {
					if (!Common.checkStartDateSale(saleReturn.getStartDate())) {
						errorsMap.put("startDate", listErrorMap.get("Sale.startDate.Validate"));
					} else if (!Common.checkEndAtSaleChild(saleReturn.getStartAt(), saleReturn.getEndAt())) {
						errorsMap.put("endAt", listErrorMap.get("Sale.endAt.ValidateTime"));
					}
				}
			}
			for (int i = 0; i < listSaleChild.size(); i++) {
				if (saleReturn.getWeekday() == listSaleChild.get(i).getWeekday()) {
					if (saleReturn.getStartAt().isBefore(listSaleChild.get(i).getEndAt())
							&& saleReturn.getEndAt().isAfter(listSaleChild.get(i).getStartAt())) {
						errorsMap.put("weekDay", listErrorMap.get("Sale.weekDay.Unique"));
					}
				}
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				User userLogin = userService.findByEmail(userDetails.getUsername());
				return ResponseEntity.ok(saleService.editSaleChild(saleReturn, userLogin));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Xóa sale parent
	 * 
	 * @param idSale
	 * @return
	 */
	@DeleteMapping("{idSale}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> deleteSaleParent(@PathVariable("idSale") Sale saleParent) {
		try {
			return ResponseEntity.ok().body(saleService.deleteSaleParent(saleParent));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * Thêm sale child
	 * 
	 * @param saleChild
	 * @return
	 */
	@PostMapping("create-sale-child")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> createSaleChild(@RequestBody Sale saleChild, Locale locale,
			@AuthenticationPrincipal UserDetails userDetails) {
		try {
			List<Sale> listSaleChild = saleService.findSaleChileBySaleParent(saleChild.getSaleParent());
			Map<String, String> listErrorMap = getListError(locale);
			Map<String, String> errorsMap = new HashMap<>();
			if (saleChild.getWeekday() == 0) {
				errorsMap.put("weekDay", listErrorMap.get("Sale.weekDay.NotBank"));
			} else if (saleChild.getWeekday() < 1 || saleChild.getWeekday() > 8) {
				errorsMap.put("weekDay", listErrorMap.get("Sale.weekDay.NotListWeek"));
			}
			// validate startDate and EndDate
			else if (saleChild.getStartDate() == null) {
				if (saleChild.getEndDate() != null) {
					errorsMap.put("startDate", listErrorMap.get("Sale.startDate.NotNull"));
				}
			} else if (saleChild.getStartDate() != null) {
				if (saleChild.getEndDate() == null) {
					errorsMap.put("endDate", listErrorMap.get("Sale.endDate.NotNull"));
				} else {
					if (!Common.checkStartDateSale(saleChild.getStartDate())) {
						errorsMap.put("startDate", listErrorMap.get("Sale.startDate.Validate"));
					} else if (!Common.checkEndDateSaleChild(saleChild.getStartDate(), saleChild.getEndDate())) {
						errorsMap.put("endDate", listErrorMap.get("Sale.endDate.ValidateSaleChild"));
					}
				}
			}
			// Validate StartAt and EndAt
			else if (saleChild.getStartAt() == null) {
				if (saleChild.getEndAt() != null) {
					errorsMap.put("startAt", listErrorMap.get("Sale.endAt.NotNull"));
				}
			} else if (saleChild.getStartAt() != null) {
				if (saleChild.getEndAt() == null) {
					errorsMap.put("startAt", listErrorMap.get("Sale.startAt.NotNull"));
				}
			} else if (saleChild.getStartAt() != null) {
				if (saleChild.getEndAt() == null) {
					errorsMap.put("endAt", listErrorMap.get("Sale.endAt.NotNull"));
				} else {
					if (!Common.checkEndAtSaleChild(saleChild.getStartAt(), saleChild.getEndAt())) {
						errorsMap.put("endAt", listErrorMap.get("Sale.endAt.ValidateTime"));
					}
				}
			}
			for (int i = 0; i < listSaleChild.size(); i++) {
				if (saleChild.getWeekday() == listSaleChild.get(i).getWeekday()) {
					if (saleChild.getStartAt().isBefore(listSaleChild.get(i).getEndAt())
							&& saleChild.getEndAt().isAfter(listSaleChild.get(i).getStartAt())) {
						errorsMap.put("weekDay", listErrorMap.get("Sale.weekDay.Unique"));
					}
				}
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				User userLogin = userService.findByEmail(userDetails.getUsername());
				return ResponseEntity.ok().body(saleService.createSaleChild(saleChild, userLogin));
			}
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
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
