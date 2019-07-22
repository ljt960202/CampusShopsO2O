package com.ljt.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljt.o2o.dao.ProductCategoryDao;
import com.ljt.o2o.dto.ProductCategoryExecution;
import com.ljt.o2o.entity.ProductCategory;
import com.ljt.o2o.enums.ProductCategoryStateEnum;
import com.ljt.o2o.exception.ProductCategoryOperationException;
import com.ljt.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if(productCategoryList!=null &&productCategoryList.size()>0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategoryList(productCategoryList);
				if(effectedNum>0) {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}else {
					throw new ProductCategoryOperationException("创建店铺失败");
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory errMsg:"+e.getMessage());
			}
			
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		// TODO 将此商品类别下的商品里的类别id置为空
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(shopId, productCategoryId);
			if(effectedNum<0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:"+e.getMessage());
		}
	}

}
