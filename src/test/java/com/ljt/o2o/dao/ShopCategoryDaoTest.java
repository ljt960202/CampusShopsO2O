package com.ljt.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	
	@Test
	public void testQueryShopCategory() {
		List<ShopCategory>  shopCategoryList = shopCategoryDao.queryShopCategory(null);
		assertEquals(1, shopCategoryList.size());
		
//		List<ShopCategory>  shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
//		assertEquals(1, shopCategoryList.size());
//		ShopCategory testCategory = new ShopCategory();
//		ShopCategory parentCategory = new ShopCategory();
//		parentCategory.setShopCategoryId(1L);
//		testCategory.setParent(parentCategory);
//		List<ShopCategory>  shopCategoryList2 = shopCategoryDao.queryShopCategory(testCategory);
//		assertEquals(1, shopCategoryList2.size());
//		System.out.println(shopCategoryList2.get(0).getShopCategoryName());
	}
	

}
