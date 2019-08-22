package com.ljt.o2o.service;

import java.util.List;

import com.ljt.o2o.dto.ImgHolder;
import com.ljt.o2o.dto.ProductExecution;
import com.ljt.o2o.entity.Product;
import com.ljt.o2o.exception.ProductOperationException;

public interface ProductService {

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
}
