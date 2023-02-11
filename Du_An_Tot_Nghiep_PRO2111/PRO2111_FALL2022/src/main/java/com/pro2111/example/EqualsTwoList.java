/**
 * DATN_FALL2022, 2022
 * EqualsTwoList.java, BUI_QUANG_HIEU
 */
package com.pro2111.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public class EqualsTwoList {

	public static List<ProductTest> listNew = Arrays.asList(new ProductTest((long)1, "Sản phẩm A"),
			new ProductTest((long)2, "Sản phẩm B"), new ProductTest((long)3, "Sản phẩm C"), new ProductTest((long)4, "Sản phẩm D"));

	public static List<ProductTest> listOld = Arrays.asList(new ProductTest((long)2, "Sản phẩm A"),
			new ProductTest((long)3, "Sản phẩm E"), new ProductTest((long)5, "Sản phẩm F"), new ProductTest((long)6, "Sản phẩm G"),
			new ProductTest((long)7, "Sản phẩm H"));

	public static Boolean checkList(List<ProductTest> listOne, List<ProductTest> listTwo) {
		return listOne.equals(listTwo);
	}

	public static List<ProductTest> getListStayTheSame(List<ProductTest> listOne, List<ProductTest> listTwo) {
		return listOne.stream().filter(two -> listTwo.stream().anyMatch(one -> one.getId().equals(two.getId())))
				.collect(Collectors.toList());
	}

	public static void main(String[] args) {
		try {
			Boolean check = checkList(listOld, listNew);
			if (check) {
				System.out.println("List không thay đổi");
			} else {
				List<ProductTest> listStayTheSame = getListStayTheSame(listNew, listOld);
				List<ProductTest> listRemove = listOld.stream()
						.filter(del -> !listStayTheSame.stream().anyMatch(old -> old.getId().equals(del.getId())))
						.collect(Collectors.toList());

				List<ProductTest> listAdd = listNew.stream()
						.filter(add -> !listStayTheSame.stream().anyMatch(news -> news.getId().equals(add.getId())))
						.collect(Collectors.toList());

				System.out.println("List StayTheSame= " + listStayTheSame);
				System.out.println("List Add= " + listAdd);
				System.out.println("List Remove= " + listRemove);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
