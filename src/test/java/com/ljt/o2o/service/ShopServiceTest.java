package com.ljt.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.dto.ImgHolder;
import com.ljt.o2o.dto.ShopExecution;
import com.ljt.o2o.entity.Area;
import com.ljt.o2o.entity.PersonInfo;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.entity.ShopCategory;
import com.ljt.o2o.enums.ShopStateEnum;
import com.ljt.o2o.exception.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testGetShopList(){
		Shop shopCondition = new Shop();
		ShopCategory sc =  new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("获取本页条数:"+se.getShopList().size());
		System.out.println("获取数据总数:"+se.getCount());
	}
	
	@Test
	public void testModifyShop() throws ShopOperationException,FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("E:\\TortoiseGit\\java\\eclipse-workspace\\up.png");
		InputStream is = new FileInputStream(shopImg);
		ImgHolder imgHolder = new ImgHolder(is, "up.png");
		ShopExecution shopExecution = shopService.modifyShop(shop,imgHolder);
		System.out.println("新图片的位置："+shopExecution.getShop().getShopImg());
	}
	@Test
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		shop.setOwner(personInfo);
		Area area = new Area();
		area.setAreaId(1L);
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
		ImgHolder imgHolder = new ImgHolder(is, shopImg.getName());
		ShopExecution shopExecution = shopService.addShop(shop,imgHolder);
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
}
