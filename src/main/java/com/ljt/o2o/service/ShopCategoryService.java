package com.ljt.o2o.service;

import java.util.List;

import com.ljt.o2o.entity.ShopCategory;


public interface ShopCategoryService {
	public static final String SHOPCATEGORYKEY = "shopcategorylist";
	
	/**
	 * 根据条件获取shopCategory列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
