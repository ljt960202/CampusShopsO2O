package com.ljt.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.dto.ImgHolder;
import com.ljt.o2o.dto.ProductExecution;
import com.ljt.o2o.entity.Product;
import com.ljt.o2o.entity.ProductCategory;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.enums.ProductStateEnum;

public class ProductServiceTest extends BaseTest{
	
	@Autowired
	private ProductService productService;
	@Test
	public void testAddProduct() throws FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		product.setProductCategory(productCategory);
		product.setProductName("测试商品名字");
		product.setProductDesc("测试商品描述");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		//创建缩略图文件流
		File thumbnailFile = new File("E:\\TortoiseGit\\java\\eclipse-workspace\\up.png");
		InputStream is = new FileInputStream(thumbnailFile);
		ImgHolder thumbnail = new ImgHolder(is,thumbnailFile.getName());
		//创建两个商品详情图片文件流并将他们添加到详情图列表中
		File publicImg1 = new File("E:\\TortoiseGit\\java\\eclipse-workspace\\duplicator.jpg");
		InputStream is1 = new FileInputStream(publicImg1);
		File publicImg2 = new File("E:\\TortoiseGit\\java\\eclipse-workspace\\pkq.jpg");
		InputStream is2 = new FileInputStream(publicImg2);
		List<ImgHolder> productImgList = new ArrayList<ImgHolder>();
		productImgList.add(new ImgHolder(is1, publicImg1.getName()));
		productImgList.add(new ImgHolder(is2, publicImg2.getName()));
		//添加商品并验证
		ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
	}
}
