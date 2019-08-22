package com.ljt.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ljt.o2o.dao.ProductDao;
import com.ljt.o2o.dao.ProductImgDao;
import com.ljt.o2o.dto.ImgHolder;
import com.ljt.o2o.dto.ProductExecution;
import com.ljt.o2o.entity.Product;
import com.ljt.o2o.entity.ProductImg;
import com.ljt.o2o.enums.ProductStateEnum;
import com.ljt.o2o.exception.ProductOperationException;
import com.ljt.o2o.service.ProductService;
import com.ljt.o2o.util.ImageUtil;
import com.ljt.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired 
	private ProductDao productDao;
	@Autowired 
	private ProductImgDao productImgDao;
	@Override
	@Transactional
	/**
	 * 1.处理缩略图，获取缩略图相对路径并赋值给product
	 * 2.往tb_product写入商品信息，获取productId
	 * 3.结合product批量处理商品详情图
	 * 4.将商品详情图列表批量插入tb_product_img中
	 */
	public ProductExecution addProduct(Product product, ImgHolder thumbnail, List<ImgHolder> productImgs)
			throws ProductOperationException {
		//空值判断
		if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null) {
			//给商品设置上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认设置为上架的状态
			product.setEnableStatus(1);
			if(thumbnail!=null) {
				addThumbnail(product,thumbnail);
			}
			try {
				//创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if(effectedNum<=0) {
					throw new ProductOperationException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品失败"+e.toString());
			}
			//若商品详细图不为空，则添加
			if(productImgs!=null&&productImgs.size()>0) {
				addProductImgList(product,productImgs);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		}else {
			//传参为空则返回错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	
	//添加缩略图
	private void addThumbnail(Product product, ImgHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}
	
	//批量添加商品详细图
	private void addProductImgList(Product product, List<ImgHolder> productImgHolderList) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgsList = new ArrayList<ProductImg>();
		for (ImgHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgsList.add(productImg);
		}
		if(productImgsList.size()>0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgsList);
				if(effectedNum<=0) {
					throw new ProductOperationException("创建商品详细图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详细图片失败:"+e.toString());
			}
		}
	}

}
