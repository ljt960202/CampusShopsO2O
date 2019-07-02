package com.ljt.o2o.dao;

import com.ljt.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 新增店铺信息
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新商铺信息
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
