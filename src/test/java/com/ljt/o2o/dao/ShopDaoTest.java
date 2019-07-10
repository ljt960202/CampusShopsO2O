package com.ljt.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

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
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小："+shopList.size());
		System.out.println("店铺总数："+count);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		shopList = shopDao.queryShopList(shopCondition, 0, 2);
		count = shopDao.queryShopCount(shopCondition);
		System.out.println("new店铺列表的大小："+shopList.size());
		System.out.println("new店铺总数："+count);
	}
	@Test
	public void testQueryShop() {
		Long shopId = 9L;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaId:"+shop.getArea().getAreaId());
		System.out.println("areaName:"+shop.getArea().getAreaName());
	}
	
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
		shop.setShopName("slave+master?4");
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
