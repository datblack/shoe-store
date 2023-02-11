/**
 * DATN_FALL2022, 2022
 * GenarateOptionAndOptionValue.java, BUI_QUANG_HIEU
 */
package com.pro2111.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.entities.Option;
import com.pro2111.service.OptionService;
import com.pro2111.utils.Constant;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("test/GenarateOptionAndOptionValue")
public class GenarateOptionAndOptionValue {
	@Autowired
	public OptionService optionService;

	@GetMapping
	public void test() {
		List<String> list = genarate();
		list.forEach(n -> {
			System.out.println(n);
		});
	}

	public List<String> genarate() {
		List<String> list = new ArrayList<>();
		List<Option> options = optionService.findByStatusLike(Constant.OPTION_STATUS_TRUE);
		options.forEach(o -> {
			o.getOptionValues().forEach(ov -> {
				if (ov.getStatus() == Constant.OPTION_VALUE_STATUS_TRUE) {
					StringBuffer customName = new StringBuffer();
					customName.append(o.getOptionName());
					customName.append(":");
					customName.append(ov.getValueName());
					list.add(customName.toString());
				}
			});
		});
		return list;
	}
}
