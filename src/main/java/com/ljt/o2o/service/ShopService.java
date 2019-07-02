package com.ljt.o2o.service;

import java.io.InputStream;

import com.ljt.o2o.dto.ShopExecution;
import com.ljt.o2o.entity.Shop;

public interface ShopService {
	ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName);
}
