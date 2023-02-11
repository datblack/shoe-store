package com.pro2111.serviceimpl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.SaleAndProductVariants;
import com.pro2111.beans.SaleAndSaleChild;
import com.pro2111.beans.VariantValueViewSaleBean;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductSale;
import com.pro2111.entities.ProductVariant;
import com.pro2111.entities.Sale;
import com.pro2111.entities.User;
import com.pro2111.entities.VariantValue;
import com.pro2111.repositories.ProductSaleRepository;
import com.pro2111.repositories.ProductVariantRepository;
import com.pro2111.repositories.SaleRepository;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.SaleService;
import com.pro2111.utils.Common;

@Service
public class SaleServiceImpl implements SaleService {
	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private ProductVariantRepository productVariantRepository;
	@Autowired
	private ProductSaleRepository productSaleRepository;
	@Autowired
	private VariantValueRepository valueRepository;

	private BigDecimal discount;

	/**
	 * Lấy ra danh sách khuyến mãi
	 * 
	 * @param 
	 * @return List<Sale>
	 */
	@Override
	public List<Sale> findAll() {
		// TODO Auto-generated method stub
		return saleRepo.findAll();
	}

	/**
	 * Tìm SaleAndProductVariants theo id sale
	 * 
	 * @param id
	 * @return SaleAndProductVariants
	 */
	@Override
	public SaleAndProductVariants findSaleAndProductById(String id) {
		SaleAndProductVariants saleAndProduct = new SaleAndProductVariants();
		Sale sale = saleRepo.findById(id).get();
		saleAndProduct.setSale(sale);
		List<ProductVariant> lisProductVariant = productSaleRepository.findProductVariantBySale(sale);
		saleAndProduct.setListProductVariants(lisProductVariant);
		return saleAndProduct;
	}

	/**
	 * Thêm mới SaleAndProductVariants
	 * 
	 * @param saleAndProduct
	 * @return Sale
	 */
	@Override
	@Transactional
	public synchronized Sale create(SaleAndProductVariants saleAndProduct) {
		Sale sale = saleAndProduct.getSale();
		if (Common.checkStartDateSaleCreate(sale.getStartDate())) {
			sale.setStatus(1);
		} else {
			sale.setStatus(0);
		}
		sale.setCreatedDate(LocalDateTime.now());
		saleRepo.save(sale);

		List<ProductVariant> listProducts = saleAndProduct.getListProductVariants();
		for (int i = 0; i < listProducts.size(); i++) {
			ProductVariant variant = listProducts.get(i);
			ProductSale productSale = new ProductSale();
			productSale.setProductVariants(variant);
			productSale.setSales(sale);
			productSale.setStatus(1);
			productSaleRepository.save(productSale);
		}
		return sale;
	}

	/**
	 * Cập nhật saleAndProduct
	 * 
	 * @param saleAndProduct
	 * @param userEdit
	 * @return SaleAndProductVariants
	 */
	@Override
	@Transactional
	public synchronized SaleAndProductVariants update(SaleAndProductVariants saleAndProduct, User userEdit) {
		SaleAndProductVariants saleAndProductUpdate = new SaleAndProductVariants();
		saleAndProduct.getSale().setUpdatedUser(userEdit);
		saleAndProduct.getSale().setUpdateDate(LocalDateTime.now());
		Sale saleUpdate = saleRepo.save(saleAndProduct.getSale());
		if (saleUpdate.getSaleType() == 0) {
			if (Common.checkStartDateSaleCreate(saleUpdate.getStartDate())) {
				saleUpdate.setStatus(1);
			} else {
				saleUpdate.setStatus(0);
			}
		}
		saleAndProductUpdate.setSale(saleUpdate);
		List<ProductVariant> listProductVariantOld = productSaleRepository.findProductVariantBySale(saleUpdate);
		List<ProductVariant> listProductVariantNew = saleAndProduct.getListProductVariants();
		if (checkList(listProductVariantOld, listProductVariantNew)) {
			saleAndProductUpdate.setListProductVariants(listProductVariantOld);
		} else {
			List<ProductVariant> listStayTheSame = getListStayTheSame(listProductVariantNew, listProductVariantOld);
			List<ProductVariant> listRemove = listProductVariantOld.stream().filter(
					del -> !listStayTheSame.stream().anyMatch(old -> old.getVariantId().equals(del.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listRemove) {
				ProductSale productSale = productSaleRepository.findByProductVariantsLikeAndSalesLike(pv, saleUpdate);
				productSaleRepository.delete(productSale);
			}
			List<ProductVariant> listAdd = listProductVariantNew.stream().filter(
					add -> !listStayTheSame.stream().anyMatch(news -> news.getVariantId().equals(add.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listAdd) {
				ProductSale productSale = new ProductSale();
				productSale.setSales(saleUpdate);
				productSale.setProductVariants(pv);
				productSale.setStatus(1);
				productSaleRepository.save(productSale);
			}
		}
		return saleAndProductUpdate;

	}

	/**
	 * Xóa chương trình khuyến mãi theo id
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public void delete(String id) {
		saleRepo.deleteById(id);
	}

	/**
	 * Lấy danh sách sản phẩm chi tiết theo list sản phẩm
	 * 
	 * @param listProduct
	 * @return List<ProductVariant>
	 */
	@Override
	public List<ProductVariant> findProductVariantsPromotionIsAllowed(List<Product> listProduct) {
		return productVariantRepository.findProductVariantsPromotionIsAllowed(listProduct);
	}

	/**
	 * checkList sản  phẩm chi tiết 
	 * 
	 * @param listOne
	 * @param listTwo
	 * @return Boolean
	 */
	public static Boolean checkList(List<ProductVariant> listOne, List<ProductVariant> listTwo) {
		return listOne.equals(listTwo);
	}

	/**
	 * Tạo mới BillDetail
	 * 
	 * @param billDetail
	 * @return
	 */
	public static List<ProductVariant> getListStayTheSame(List<ProductVariant> listOne, List<ProductVariant> listTwo) {
		return listOne.stream()
				.filter(two -> listTwo.stream().anyMatch(one -> one.getVariantId().equals(two.getVariantId())))
				.collect(Collectors.toList());
	}

	/**
	 * Kiểm tra tên chương trình khuyến mãi
	 * 
	 * @param saleNew
	 * @param saleOld
	 * @param method
	 * @return Boolean
	 */
	@Override
	public Boolean checkSaleName(Sale saleNew, Sale saleOld, String method) {
		Boolean checkBoolean = true;
		if ("POST".equalsIgnoreCase(method)) {
			Sale sale = saleRepo.findSaleByName(saleNew.getSaleName());
			if (sale != null) {
				checkBoolean = false;
			}
		} else if ("PUT".equalsIgnoreCase(method)) {
			if (!saleNew.getSaleName().equalsIgnoreCase(saleOld.getSaleName())) {
				Sale sale = saleRepo.findSaleByName(saleNew.getSaleName());
				if (sale != null) {
					checkBoolean = false;
				}
			}
		}
		return checkBoolean;
	}

	/**
	 * Tìm chương trình khuyến mãi theo mã
	 * 
	 * @param id
	 * @return Sale
	 */
	@Override
	public Sale findSaleById(String id) {
		return saleRepo.findById(id).get();
	}

	/**
	 * Lấy danh sách khuyến mãi theo trạng thái
	 * 
	 * @param i
	 * @return List<Sale>
	 */
	@Override
	public List<Sale> findByStatus(int i) {
		return saleRepo.findByStatus(i);
	}

	/**
	 * Cập nhật list khuyến mãi
	 * 
	 * @param listSaleUpdate
	 * @return List<Sale
	 */
	@Override
	public List<Sale> updateSale(List<Sale> listSaleUpdate) {
		return saleRepo.saveAll(listSaleUpdate);
	}

	/**
	 * Tìm discountSale theo sản phẩm chi tiết
	 * 
	 * @param productVariant
	 * @return BigDecimal
	 */
	@Override
	public BigDecimal findDiscountSaleByProductVariant(ProductVariant productVariant) {
		List<Sale> sales = productSaleRepository.findSalesByProductVariant(productVariant);
		discount = new BigDecimal(0);
		sales.forEach(s -> {
			if (s.getDiscountType() == 0) {
				discount = discount.add(s.getDiscount());
			} else {
				BigDecimal discountSale = productVariant.getPrice().multiply(s.getDiscount())
						.divide(BigDecimal.valueOf(100));
				discount = discount.add(discountSale);
			}
		});
		return discount;
	}

	/**
	 * Tìm VariantValueViewSaleBean theo sản phẩm chi tiết
	 * 
	 * @param productVariant
	 * @return VariantValueViewSaleBean
	 */
	@Override
	public VariantValueViewSaleBean findVariantValueByProductVariant(ProductVariant productVariant) {
		VariantValueViewSaleBean bean = new VariantValueViewSaleBean();
		List<VariantValue> list = valueRepository.findByProductVariantsLike(productVariant);
		for (int i = list.size() - 1; i >= 0; i--) {
			VariantValue value = list.get(i);
			if (value.getOptionValues().getIsShow() == 0 && value.getProductOptions().getOptions().getIsShow() == 0) {
				list.remove(i);
			}
		}
		List<Sale> listSale = productSaleRepository.findSalesByProductVariant(productVariant);
		List<String> listName = new ArrayList<String>();
		listSale.forEach(s -> {
			listName.add(s.getSaleName());
		});
		bean.setVariantValues(list);
		bean.setCountSale(listSale.size());
		bean.setSaleName(listName);
		return bean;
	}

	/**
	 * Tạo mới sale cha và sale con
	 * 
	 * @param saleAndSaleChild
	 * @param userCreate
	 * @return Sale
	 */
	@Override
	@Transactional
	public synchronized Sale createAndSaleChild(SaleAndSaleChild saleAndSaleChild, User userCreate) {
		Sale saleParent = saleAndSaleChild.getSaleParent();
		saleParent.setCreatedDate(LocalDateTime.now());
		saleParent.setCreatedUser(userCreate);
		saleParent.setWeekday(0);
		saleRepo.save(saleParent);
		List<Sale> lstSaleChild = saleAndSaleChild.getLstSaleChild();
		for (int i = 0; i < lstSaleChild.size(); i++) {
			Sale salechild = lstSaleChild.get(i);
			if (salechild == null) {
				break;
			}
			salechild.setDiscountType(0);
			salechild.setDiscount(null);
			salechild.setSaleName("");
			salechild.setSaleType(saleParent.getSaleType());
			salechild.setSaleParent(saleParent);
			saleRepo.save(salechild);
		}
		List<ProductVariant> listProducts = saleAndSaleChild.getListProductVariants();
		for (int i = 0; i < listProducts.size(); i++) {
			ProductVariant variant = listProducts.get(i);
			ProductSale productSale = new ProductSale();
			productSale.setProductVariants(variant);
			productSale.setSales(saleParent);
			productSale.setStatus(1);
			productSaleRepository.save(productSale);
		}

		return saleParent;
	}

	/**
	 * Cập nhật sale và sale con
	 * 
	 * @param saleAndSaleChild
	 * @return SaleAndSaleChild
	 */
	@Override
	@Transactional
	public synchronized SaleAndSaleChild updateAndSaleChild(SaleAndSaleChild saleAndSaleChild) {
		SaleAndProductVariants saleAndProductUpdate = new SaleAndProductVariants();
		Sale saleUpdate = saleRepo.save(saleAndSaleChild.getSaleParent());
		if (Common.checkStartDateSaleCreate(saleUpdate.getStartDate())) {
			saleUpdate.setStatus(1);
		} else {
			saleUpdate.setStatus(0);
		}
		saleAndProductUpdate.setSale(saleUpdate);
		List<ProductVariant> listProductVariantOld = productSaleRepository.findProductVariantBySale(saleUpdate);
		List<ProductVariant> listProductVariantNew = saleAndSaleChild.getListProductVariants();
		if (checkList(listProductVariantOld, listProductVariantNew)) {
			saleAndProductUpdate.setListProductVariants(listProductVariantOld);
		} else {
			List<ProductVariant> listStayTheSame = getListStayTheSame(listProductVariantNew, listProductVariantOld);
			List<ProductVariant> listRemove = listProductVariantOld.stream().filter(
					del -> !listStayTheSame.stream().anyMatch(old -> old.getVariantId().equals(del.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listRemove) {
				ProductSale productSale = productSaleRepository.findByProductVariantsLikeAndSalesLike(pv, saleUpdate);
				productSaleRepository.delete(productSale);
			}
			List<ProductVariant> listAdd = listProductVariantNew.stream().filter(
					add -> !listStayTheSame.stream().anyMatch(news -> news.getVariantId().equals(add.getVariantId())))
					.collect(Collectors.toList());
			for (ProductVariant pv : listAdd) {
				ProductSale productSale = new ProductSale();
				productSale.setSales(saleUpdate);
				productSale.setProductVariants(pv);
				productSale.setStatus(1);
				productSaleRepository.save(productSale);
			}
		}
		return saleAndSaleChild;
	}

	/**
	 * Lấy danh sách sale cha
	 * 
	 * @param 
	 * @return List<Sale>
	 */
	@Override
	public List<Sale> findSaleParent() {
		return saleRepo.findAllSaleParent();
	}

	/**
	 * Tìm kiếm sale cha và sale con theo id
	 * 
	 * @param idSale
	 * @return SaleAndSaleChild
	 */
	@Override
	public SaleAndSaleChild findSaleAndSaleChildBySaleParent(String idSale) {
		Sale saleParent = saleRepo.findById(idSale).get();
		List<Sale> lstSaleChild = saleRepo.findSaleChileBySaleParent(saleParent.getSaleId());
		List<ProductVariant> lstProductVariants = productSaleRepository.findProductVariantBySale(saleParent);
		SaleAndSaleChild saleAndSaleChild = new SaleAndSaleChild();
		saleAndSaleChild.setSaleParent(saleParent);
		saleAndSaleChild.setLstSaleChild(lstSaleChild);
		saleAndSaleChild.setListProductVariants(lstProductVariants);
		return saleAndSaleChild;
	}

	/**
	 * Xóa sale con
	 * 
	 * @param saleChild
	 * @param userEdit
	 * @return Sale
	 */
	@Override
	public Sale deleteSaleChild(Sale saleChild, User userEdit) {
		saleChild.getSaleParent().setUpdateDate(LocalDateTime.now());
		saleChild.getSaleParent().setUpdatedUser(userEdit);
		saleRepo.save(saleChild.getSaleParent());
		saleRepo.delete(saleChild);
		return saleChild;
	}
	
	/**
	 * Lấy danh sách sale con theo trạng thái
	 * 
	 * @param i
	 * @return List<Sale>
	 */
	@Override
	public List<Sale> findSaleChildByStatus(int i) {
		return saleRepo.findSaleChildByStatus(i);
	}

	/**
	 * Cập nhật chương trình khuyến mãi con
	 * 
	 * @param saleChild
	 * @param userEdit
	 * @return
	 */
	@Override
	public Sale editSaleChild(Sale saleChild, User userEdit) {
		LocalDateTime dateTimeNow = LocalDateTime.now();
		long secondNow = dateTimeNow.toEpochSecond(ZoneOffset.UTC);
		long secondInput = saleChild.getEndDate().toEpochSecond(ZoneOffset.UTC);
		long minute = (secondInput - secondNow) / 60;
		if (saleChild.getCreatedDate() == null) {
			if (saleChild.getStartAt() == null) {
				if (DayOfWeek.of(saleChild.getWeekday()).equals(LocalDate.now().getDayOfWeek())) {
					saleChild.setStatus(1);
					saleChild.getSaleParent().setStatus(1);
				} else {
					saleChild.setStatus(0);
					saleChild.getSaleParent().setStatus(0);
				}
			} else {
				if (DayOfWeek.of(saleChild.getWeekday()).equals(LocalDate.now().getDayOfWeek())
						&& Common.checkTimeToLocal(saleChild.getStartAt())) {
					saleChild.setStatus(1);
					saleChild.getSaleParent().setStatus(1);
				} else {
					saleChild.setStatus(0);
					saleChild.getSaleParent().setStatus(0);
				}
			}
		} else {
			if (saleChild.getStartAt() == null) {
				if (minute <= 0 && Common.checkTimeToLocal(saleChild.getStartAt())) {
					saleChild.setStatus(1);
					saleChild.getSaleParent().setStatus(1);
				} else {
					saleChild.setStatus(0);
					saleChild.getSaleParent().setStatus(0);
				}
			} else {
				if (minute <= 0 && Common.checkTimeToLocal(saleChild.getStartAt())
						&& DayOfWeek.of(saleChild.getWeekday()).equals(LocalDate.now().getDayOfWeek())) {
					saleChild.setStatus(1);
					saleChild.getSaleParent().setStatus(1);
				} else {
					saleChild.setStatus(0);
					saleChild.getSaleParent().setStatus(0);
				}

			}
		}
		saleChild.getSaleParent().setUpdatedUser(userEdit);
		saleChild.getSaleParent().setUpdateDate(dateTimeNow);
		this.saleRepo.save(saleChild.getSaleParent());
		return saleRepo.save(saleChild);
	}

	/**
	 * Lấy danh sách saleChild theo saleParent
	 * 
	 * @param saleParent
	 * @return List<Sale>
	 */
	@Override
	public List<Sale> findSaleChileBySaleParent(Sale saleParent) {
		return saleRepo.findSaleChileBySaleParent(saleParent.getSaleId());
	}

	/**
	 * Xóa sale cha 
	 * 
	 * @param saleParent
	 * @return Sale
	 */
	@Override
	@Transactional
	public Sale deleteSaleParent(Sale saleParent) {
		if (saleParent.getSaleType() == 0) {
			productSaleRepository.deleteProductSaleBySale(saleParent);
			saleRepo.delete(saleParent);
		} else if (saleParent.getSaleType() == 1) {
			productSaleRepository.deleteProductSaleBySale(saleParent);
			saleRepo.deleteSaleChildBySaleParent(saleParent);
			saleRepo.delete(saleParent);
		}
		return saleParent;
	}

	/**
	 * Tạo mới sale con
	 * 
	 * @param saleChlid
	 * @param userEdit
	 * @return Sale
	 */
	@Override
	public Sale createSaleChild(Sale saleChlid, User userEdit) {
		saleChlid.setDiscountType(0);
		saleChlid.setDiscount(null);
		saleChlid.setSaleName("");
		saleChlid.setSaleType(1);
		saleChlid.getSaleParent().setUpdatedUser(userEdit);
		this.saleRepo.save(saleChlid.getSaleParent());
		return saleRepo.save(saleChlid);
	}

}
