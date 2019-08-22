package com.ljt.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ljt.o2o.entity.Product;

public interface ProductDao {
	/**
	 * 分页查询商品列表信息,可输入的条件有商品名(模糊),商品状态，店铺Id,商品类别,
	 * @param productCondition 
	 * @param rowIndex 从第几行开始取数据
	 * @param pageSize 返回的条数
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition")Product productCondition,
			@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	
	/**
	 * 更新商品
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	
	
	int queryProductCount(@Param("productCondition")Product productCondition);
}
