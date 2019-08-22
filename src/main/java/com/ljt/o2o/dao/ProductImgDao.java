package com.ljt.o2o.dao;

import java.util.List;

import com.ljt.o2o.entity.ProductImg;

public interface ProductImgDao {
	
	List<ProductImg> queryProductImgList(long productId);
	/**
	 * 批量添加商品的详细图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);

	int deleteProductImgByProductId(long productId);
}
