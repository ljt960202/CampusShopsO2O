package com.ljt.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.entity.Area;
import com.ljt.o2o.entity.PersonInfo;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	
	@Autowired
	private ShopDao shopDao;

	
	@Test
	public void testInsertShop() {
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
		shop.setShopName("name");
		shop.setShopDesc("desc");
		shop.setShopAddr("addr");
		shop.setPhone("110");
		shop.setShopImg("img");
		shop.setPriority(1);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("advice");
		int num = shopDao.insertShop(shop);
		assertEquals(1, num);
	}
	
	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("测试name");
		shop.setShopDesc("测试desc");
		shop.setLastEditTime(new Date());
		int num = shopDao.updateShop(shop);
		assertEquals(1, num);
	}
}
