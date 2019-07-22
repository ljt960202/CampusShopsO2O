package com.ljt.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ljt.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 通过shopId查询店铺商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**
	 * 批量插入商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategoryList(List<ProductCategory> productCategory);
	
	/**
	 * 删除商品类别 
	 * @param shopId
	 * @param productCategoryId
	 * @return
	 */
	int deleteProductCategory(@Param("shopId")long shopId,@Param("productCategoryId")long productCategoryId);
}
