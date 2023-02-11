/**
 * Luvina Software JSC, 2022
 * ProductServiceIml.java, Bui Quang Hieu
 */
package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.pro2111.beans.ProAndOpAndOv;
import com.pro2111.beans.ProductVariansBean;
import com.pro2111.beans.PvAndOv;
import com.pro2111.beans.PvAndVv;
import com.pro2111.entities.Image;
import com.pro2111.entities.Option;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.User;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.ImageRepository;
import com.pro2111.repositories.ProductOptionRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.ProductVariantService;
import com.pro2111.service.UserService;
import com.pro2111.utils.Common;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 */

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private VariantValueRepository valueRepository;

	@Autowired
	private ProductOptionRepository productOptionRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserService userService;

	Integer _count = 0;
	Integer _countExsits = 0;
	Boolean _checkCondition = true;

	String customName = "";

	List<ProductVariant> subListByOption;

	List<ProductVariant> subListByOptionValue;

	ProductVariant productVariant;

	private List<Option> lstOption;

	private List<OptionValue> lstOptionValue;

	private List<String> optionNames;
	private List<String> optionValueNames;

	/**
	 * Lấy tất cả ProductVariant
	 */
	@Override
	public synchronized List<ProductVariant> findAll() {
		return productVariantRepository.findAll();
	}

	/**
	 * Lấy ProductVariant theo id
	 */
	@Override
	public synchronized ProductVariant findById(Long id) {
		return productVariantRepository.findById(id).get();
	}

	/**
	 * Tạo mới ProductVariant
	 */
	@Override
	public synchronized ProductVariant create(ProductVariant productVariant) {
		return productVariantRepository.save(productVariant);
	}

	/**
	 * Lấy khoảng giá min và max của tất cả ProductVariant
	 */
	@Override
	public Object getPriceRange() {
		return productVariantRepository.getPriceRange();
	}

	/**
	 * Cập nhật ProductVariant từ PvAndVv
	 */
	@Override
	@Transactional
	public synchronized ProductVariant update(PvAndVv pvAndVv) {
		ProductVariant pvOld = productVariantRepository.findById(pvAndVv.getProductVariant().getVariantId()).get();
		ProductVariant productVariant = pvAndVv.getProductVariant();
		pvOld.setSkuId(productVariant.getSkuId());
		pvOld.setStatus(productVariant.getStatus());
		pvOld.setProducts(productVariant.getProducts());
		pvOld.setPrice(productVariant.getPrice());
		pvOld.setQuantity(productVariant.getQuantity());
		pvOld.setQuantityError(productVariant.getQuantityError());
		pvOld.setIsSale(productVariant.getIsSale());
		pvOld.setImportPrice(productVariant.getImportPrice());
		pvOld.setTax(productVariant.getTax());
		pvOld.setUserEdit(productVariant.getUserEdit());
		pvOld.setEditDate(LocalDateTime.now());
		productVariantRepository.save(pvOld);

		optionNames = new ArrayList<>();
		optionValueNames = new ArrayList<>();
		pvAndVv.getVariantValues().forEach(value -> {
			optionNames.add(value.getProductOptions().getOptions().getOptionName());
			optionValueNames.add(value.getOptionValues().getValueName());
		});
		pvOld.setSkuId(Common.genarateSkuID(optionNames, optionValueNames));
		productVariantRepository.save(pvOld);
		valueRepository.deleteAll(pvOld);
		for (int i = 0; i < pvAndVv.getVariantValues().size(); i++) {
			VariantValue value = pvAndVv.getVariantValues().get(i);
			valueRepository.createVariantValue(pvOld, value.getProductOptions().getProducts(),
					value.getProductOptions().getOptions(), value.getOptionValues());
		}

		return pvOld;
	}

	/**
	 * Lấy list ProductVariant theo Product
	 */
	@Override
	public synchronized List<ProductVariant> findByProduct(Product product) {
		return productVariantRepository.findByProductsLike(product);
	}

	/**
	 * Lấy list ProductVariant được phép bán
	 */
	@Override
	public synchronized List<ProductVariant> getProductVariantOfSale() {
//		return productVariantRepository.getProductVariantOfSale();
		List<ProductVariant> productVariantsReturn = productVariantRepository.getProductVariantOfSale();
		productVariantsReturn = Common.customNameProductByProductVariant(productVariantsReturn);
		return productVariantsReturn;
	}

	/**
	 * Tạo ProductVariant và VariantValue
	 */
	@Override
	@Transactional
	public synchronized ProductVariant createProductVariantAndVariantValue(ProductVariant productVariant,
			List<VariantValue> variantValue) {
		List<ProductVariant> lstPv = productVariantRepository.findByProductsLike(productVariant.getProducts());

		lstPv.forEach(pv -> {
			List<VariantValue> lstVv = valueRepository.findByProductVariantsLike(pv);

			if (lstVv.size() == variantValue.size()) {

				for (int i = 0; i < lstVv.size(); i++) {
					for (int j = 0; j < variantValue.size(); j++) {
						if (lstVv.get(i).getOptionValues().getValueId() == variantValue.get(j).getOptionValues()
								.getValueId()) {
							_checkCondition = false;

						}
					}
					if (!_checkCondition) {
						_countExsits++;
					}
					_checkCondition = true;
				}
			}

			if (_countExsits == lstVv.size()) {
				_count++;
			}
			_countExsits = 0;
		});

		if (_count == 0) {
			ProductVariant pvNew = productVariantRepository.save(productVariant);
			for (int i = 0; i < variantValue.size(); i++) {
				valueRepository.createVariantValue(pvNew, variantValue.get(i).getProductOptions().getProducts(),
						variantValue.get(i).getProductOptions().getOptions(), variantValue.get(i).getOptionValues());

			}
			return pvNew;
		} else {
			_count = 0;
			return null;
		}

	}

	/**
	 * Lấy list ProductVariant theo ProductName gần đúng
	 */
	@Override
	public synchronized List<ProductVariant> findByApproximateProductName(String name) {
		return productVariantRepository.findByApproximateProductName(name);
	}

	/**
	 * Lấy list ProductVariant theo mọi key
	 */
	@Override
	public synchronized List<ProductVariant> dynamicSearchByKey(String inputString) {
		// Khai báo list ProductVariant
		List<ProductVariant> lstProductVariants = new ArrayList<ProductVariant>();

		subListByOption = new ArrayList<>();
		subListByOptionValue = new ArrayList<>();
		// Product
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

			QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
					.forEntity(Product.class).get();

			org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("productName").matching(inputString)
					.createQuery();

			org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
					Product.class);

			List<Product> lstProducts = jpaQuery.getResultList();
			lstProducts.forEach(p -> {
				lstProductVariants.addAll(new ArrayList<>(p.getProductVariants()));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Option
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

			QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
					.forEntity(Option.class).get();

			org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("optionName").matching(inputString)
					.createQuery();

			org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
					Option.class);

			List<Option> listOptions = jpaQuery.getResultList();
			listOptions.forEach(op -> {
				List<ProductOption> lstPo = op.getProductOptions();

				lstPo.forEach(po -> {
					List<ProductVariant> subList = new ArrayList<>(po.getProducts().getProductVariants());
					subListByOption.addAll(subList);
				});

			});

			if (subListByOption.size() != 0) {
				if (lstProductVariants.size() == 0) {
					lstProductVariants.addAll(subListByOption);
				} else {
					for (int i = lstProductVariants.size() - 1; i >= 0; i--) {
						if (!subListByOption.contains(lstProductVariants.get(i))) {
							lstProductVariants.remove(i);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// OptionValue
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

			QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
					.forEntity(OptionValue.class).get();

			org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("valueName").matching(inputString)
					.createQuery();

			org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
					OptionValue.class);

			List<OptionValue> lstProducts = jpaQuery.getResultList();
			lstProducts.forEach(ov -> {

				List<VariantValue> lstVv = new ArrayList<>(ov.getVariantValues());
				lstVv.forEach(vv -> {
					if (vv.getStatus() == 1) {
						subListByOptionValue.add(vv.getProductVariants());
					}
				});

			});

			if (subListByOptionValue.size() != 0) {
				if (lstProductVariants.size() == 0) {
					lstProductVariants.addAll(subListByOptionValue);
				} else {
					for (int i = lstProductVariants.size() - 1; i >= 0; i--) {
						if (!subListByOptionValue.contains(lstProductVariants.get(i))) {
							lstProductVariants.remove(i);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = lstProductVariants.size() - 1; i >= 0; i--) {
			if (lstProductVariants.get(i).getStatus() == 0) {
				lstProductVariants.remove(i);
			}
		}

		return lstProductVariants;
	}

	/**
	 * Lấy ProductVariant theo Product, Option, Option Value
	 */
	@Override
	public synchronized ProductVariant findByProductAndOptionAndOptionValue(ProAndOpAndOv proAndOpAndOv) {
		productVariant = null;
		List<ProductVariant> listProductVariants = findByProduct(proAndOpAndOv.getProduct());
		listProductVariants.forEach(pv -> {
			List<VariantValue> lstVv = new ArrayList<>(pv.getVariantValues());
			lstOption = new ArrayList<>();
			lstOptionValue = new ArrayList<>();
			for (int i = 0; i < lstVv.size(); i++) {
				VariantValue value = lstVv.get(i);
				if (value.getProductOptions().getOptions().getIsShow() == 1
						&& value.getOptionValues().getIsShow() == 1) {
					lstOption.add(value.getProductOptions().getOptions());
					lstOptionValue.add(value.getOptionValues());
				}
			}

			// Sort
			lstOption.sort(Comparator.comparing(Option::getOptionId));
			lstOptionValue.sort(Comparator.comparing(OptionValue::getValueId));
			proAndOpAndOv.getOptions().sort(Comparator.comparing(Option::getOptionId));
			proAndOpAndOv.getOptionValues().sort(Comparator.comparing(OptionValue::getValueId));

			if (proAndOpAndOv.getOptions().equals(lstOption) && proAndOpAndOv.getOptionValues().equals(lstOptionValue)
					&& pv.getStatus() == Constant.STATUS_TRUE_PRODUCT_VARIANT) {
				productVariant = pv;
			}
		});

		return productVariant;
	}

	/**
	 * Lấy giá min ProductVariant theo Product
	 */
	@Override
	public synchronized BigDecimal findMinPriceByProduct(Product product) {
		return productVariantRepository.findMinPriceByProduct(product);
	}

	/**
	 * Lấy giá max ProductVariant theo Product
	 */
	@Override
	public synchronized BigDecimal findMaxPriceByProduct(Product product) {
		return productVariantRepository.findMaxPriceByProduct(product);
	}

	/**
	 * Tạo mới ProductVariant bằng PvAndOv
	 */
	@Override
	@Transactional
	public List<ProductVariant> createV2(List<PvAndOv> pvAndVvs) {
		List<ProductVariant> listReturn = new ArrayList<ProductVariant>();

		pvAndVvs.forEach(item -> {
			optionNames = new ArrayList<>();
			optionValueNames = new ArrayList<>();
			item.getOptionValues().forEach(ov -> {
				optionNames.add(ov.getOptions().getOptionName());
				optionValueNames.add(ov.getValueName());
			});
			item.getProductVariant().setSkuId(Common.genarateSkuID(optionNames, optionValueNames));
			item.getProductVariant().setStatus(1);
			item.getProductVariant().setCreateDate(LocalDateTime.now());
			item.getProductVariant().setEditDate(LocalDateTime.now());
			ProductVariant pv = productVariantRepository.save(item.getProductVariant());
			item.getOptionValues().forEach(ov -> {
				ProductOption po = productOptionRepository.findByProductsLikeAndOptionsLike(pv.getProducts(),
						ov.getOptions());
				VariantValue vv = new VariantValue();
				vv.setProductVariants(pv);
				vv.setOptionValues(ov);
				vv.setProductOptions(po);
				vv.setStatus(1);
//				valueRepository.save(vv);
				valueRepository.createVariantValue(pv, vv.getProductOptions().getProducts(),
						vv.getProductOptions().getOptions(), ov);
			});

			productVariantRepository.save(pv);
			item.getImages().forEach(i -> {
				Image image = new Image();
				image.setProductVariants(pv);
				image.setImagePath(i);
				imageRepository.save(image);
			});
			listReturn.add(pv);
		});
		return listReturn;
	}

	/**
	 * Cập nhật đơn giá ProductVariant
	 */
	@Override
	public ProductVariansBean updatePrice(ProductVariansBean bean) {
		User user = userService.findById(bean.getUserId());
		bean.getProductVariants().forEach(pv -> {
			ProductVariant productVariant = productVariantRepository.findById(pv.getVariantId()).get();
			productVariant.setImportPrice(pv.getImportPrice());
			productVariant.setPrice(pv.getPrice());
			productVariant.setTax(pv.getTax());
			productVariant.setUserEdit(user);
			productVariant.setEditDate(LocalDateTime.now());
			productVariantRepository.save(productVariant);
		});
		return bean;
	}

	/**
	 * Cập nhật status ProductVariant
	 */
	@Override
	public ProductVariant updateStatus(ProductVariant productVariant) {
		productVariant.setEditDate(LocalDateTime.now());
		if (Constant.STATUS_TRUE_PRODUCT_VARIANT == productVariant.getStatus()) {
			productVariant.setStatus(Constant.STATUS_FALSE_PRODUCT_VARIANT);
			return productVariantRepository.save(productVariant);
		} else if (Constant.STATUS_FALSE_PRODUCT_VARIANT == productVariant.getStatus()) {
			productVariant.setStatus(Constant.STATUS_TRUE_PRODUCT_VARIANT);
			return productVariantRepository.save(productVariant);
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}

	}

}
