package com.ljt.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljt.o2o.dao.ShopCategoryDao;
import com.ljt.o2o.entity.ShopCategory;
import com.ljt.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

}
