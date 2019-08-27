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

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	/**
	 * 1.若缩略图参数有值，则处理缩略图，若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
	 * 2.若商品详情图列表参数有值，对商品详细图片列表进行同样的操作
	 * 3.将tb_produc_img下面的该商品原先的商品详情图记录全部清除
	 * 4.更新tb_product的信息
	 * 
	 */
	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImgHolder thumbnail, List<ImgHolder> productImgHolderList)
			throws ProductOperationException {
		//空值判断
		if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null) {
			//给商品设置上默认属性
			product.setLastEditTime(new Date());
			//若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if(thumbnail!=null) {
				//先获取一遍原有信息，因为原来的信息里有原图片地址
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if(tempProduct.getImgAddr()!=null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			//如果有新的存入的商品详细图，则将原先的删除，并添加新的图片
			if(productImgHolderList!=null&&productImgHolderList.size()>0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgHolderList);
			}
			try {
				//更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if(effectedNum<=0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			} catch (Exception e) {
				throw new ProductOperationException("更新商品信息失败："+e.toString());
			}
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	
	private void deleteProductImgList(long productId) {
		//根据productId获取原来的图片
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		if(productImgList!=null&&productImgList.size()>0) {
			for (ProductImg productImg : productImgList) {
				String imgAddr = productImg.getImgAddr();
				if(imgAddr!=null) {
					//干掉原来的图片
					ImageUtil.deleteFileOrPath(imgAddr);
				}
			}
		}
		//刪除数据库里原有图片的信息
		productImgDao.deleteProductImgByProductId(productId);
	}

}
