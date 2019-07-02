package com.ljt.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljt.o2o.dao.ShopDao;
import com.ljt.o2o.dto.ShopExecution;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.enums.ShopStateEnum;
import com.ljt.o2o.exception.ShopOperationException;
import com.ljt.o2o.service.ShopService;
import com.ljt.o2o.util.ImageUtil;
import com.ljt.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	@Override
	public ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) {
		if(shop==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋初始值
			shop.setEnableStatus(ShopStateEnum.CHECK.getState());
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum<=0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(shopImgInputStream!=null) {
					try {
						//存储图片
						addShopImg(shop,shopImgInputStream,fileName);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					//更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum<=0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
			
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	
	
	
	
	private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
		//获取shop图片目录的相对值路径
		String desc = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName, desc) ;
		shop.setShopImg(shopImgAddr);
	}

}
