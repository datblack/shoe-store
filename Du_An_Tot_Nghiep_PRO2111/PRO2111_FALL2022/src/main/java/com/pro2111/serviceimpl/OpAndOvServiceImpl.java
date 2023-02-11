/**
 * DATN_FALL2022, 2022
 * OpAndOvServiceIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro2111.beans.OptionAndOptionValue;
import com.pro2111.entities.OptionValue;
import com.pro2111.entities.Product;
import com.pro2111.entities.ProductOption;
import com.pro2111.repositories.VariantValueRepository;
import com.pro2111.service.OpAndOvService;
import com.pro2111.service.ProductOptionService;
import com.pro2111.service.VariantValueService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class OpAndOvServiceImpl implements OpAndOvService {

	@Autowired
	ProductOptionService productOptionService;

	@Autowired
	VariantValueService variantValueService;

	@Autowired
	VariantValueRepository repository;

	/**
	 * Lấy list OptionAndOptionValue theo Product
	 */
	@Override
	public synchronized List<OptionAndOptionValue> findByProduct(Product product) {
		List<OptionAndOptionValue> optionAndOptionValues = new ArrayList<>();

		// findListProductOption by Product
		List<ProductOption> lstPo = productOptionService.findByProduct(product);
		lstPo.forEach(po -> {
			if (po.getOptions().getIsShow() == 1) {
				OptionAndOptionValue opAndOv = new OptionAndOptionValue();

				List<OptionValue> lstOv = new ArrayList<OptionValue>();

				po.getVariantValues().forEach(vv -> {
					if (vv.getStatus() == 1 && vv.getOptionValues().getIsShow() == 1
							&& vv.getProductVariants().getStatus() == 1) {
						if (!lstOv.contains(vv.getOptionValues())) {
							lstOv.add(vv.getOptionValues());
						}

					}
				});

				opAndOv.setOptionValues(lstOv);
				opAndOv.setOption(po.getOptions());
				optionAndOptionValues.add(opAndOv);
			}
		});

		for (int i = optionAndOptionValues.size() - 1; i >= 0; i--) {
			OptionAndOptionValue value = optionAndOptionValues.get(i);
			if (value.getOptionValues().size() == 0) {
				optionAndOptionValues.remove(i);
				System.out.println(value);
			}
		}
		return optionAndOptionValues;
	}

}
