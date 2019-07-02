package com.ljt.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.dto.ShopExecution;
import com.ljt.o2o.entity.Area;
import com.ljt.o2o.entity.PersonInfo;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.entity.ShopCategory;
import com.ljt.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {
	
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		shop.setOwner(personInfo);
		Area area = new Area();
		area.setAreaId(1);
		shop.setArea(area);
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(1L);
		shop.setShopCategory(shopCategory);
		shop.setShopName("店铺4");
		
		shop.setShopDesc("desc4");
		shop.setShopAddr("addr4");
		shop.setPhone("110");
		shop.setAdvice("审核中");
		File shopImg = new File("E:\\TortoiseGit\\java\\eclipse-workspace\\duplicator.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.addShop(shop, is,shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
}
