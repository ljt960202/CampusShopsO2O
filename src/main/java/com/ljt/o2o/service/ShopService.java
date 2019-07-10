package com.ljt.o2o.service;

import java.io.InputStream;

import com.ljt.o2o.dto.ShopExecution;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.exception.ShopOperationException;

public interface ShopService {
	
	/**
	 * 根据shopCondition分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	
	/**
	 *  通过ID获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	/**
	 * 
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 */
	ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
	/**
	 * 注册店铺信息，包括对图片的处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 */
	ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
