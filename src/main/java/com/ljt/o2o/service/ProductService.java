package com.ljt.o2o.service;

import java.util.List;

import com.ljt.o2o.dto.ImgHolder;
import com.ljt.o2o.dto.ProductExecution;
import com.ljt.o2o.entity.Product;
import com.ljt.o2o.exception.ProductOperationException;

public interface ProductService {
	
	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别
	 * @param productCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);

	/**
	 * 添加商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product,ImgHolder thumbnail, 
			List<ImgHolder> productImgs)throws ProductOperationException;
	
	/**
	 * 通过商品ID查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	
	/**
	 * 修改商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution modifyProduct(Product product,ImgHolder thumbnail, 
			List<ImgHolder> productImgs)throws ProductOperationException;
}
