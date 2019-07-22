package com.ljt.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	
	@Test
	public void testBatchInsertProductCategoryList() {
		ProductCategory productCategory = new ProductCategory(); 
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(12L);
		ProductCategory productCategory2 =  new ProductCategory(); 
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(12L);
		List<ProductCategory> list =  new ArrayList<ProductCategory>();
		list.add(productCategory);
		list.add(productCategory2);
		int effectedNum = productCategoryDao.batchInsertProductCategoryList(list);
		assertEquals(2, effectedNum);
	}
	
	@Test
	public void testQueryProductCategoryList() {
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(1);
		System.out.println("商品分类数量"+productCategoryList.size());
	}
	@Test
	public void testDeleteProductCategory() {
		int shopId = 12;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory productCategory : productCategoryList) {
			if("商品类别11".equals(productCategory.getProductCategoryName())||"商品类别12".equals(productCategory.getProductCategoryName())) {
				int effectedNum = productCategoryDao.deleteProductCategory(shopId, productCategory.getProductCategoryId());
				assertEquals(1, effectedNum);
			}
		}
	}
}
