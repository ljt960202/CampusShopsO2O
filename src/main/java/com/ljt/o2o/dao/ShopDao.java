package com.ljt.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ljt.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	
	/**
	 * 分页查询店铺列表信息,可输入的条件有店铺名(模糊),店铺状态,店铺类别,区域Id,owner
	 * @param shopCondition 
	 * @param rowIndex 从第几行开始取数据
	 * @param pageSize 返回的条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
			@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	
	/**
	 * 查询店铺信息通过ID
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(Long shopId);
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
