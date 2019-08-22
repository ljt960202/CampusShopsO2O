package com.ljt.o2o.web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljt.o2o.dto.ProductCategoryExecution;
import com.ljt.o2o.dto.Result;
import com.ljt.o2o.entity.ProductCategory;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.enums.ProductCategoryStateEnum;
import com.ljt.o2o.exception.ProductCategoryOperationException;
import com.ljt.o2o.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

	@Autowired 
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value="/getproductcategorylist",method=RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if(currentShop!=null&&currentShop.getShopId()>0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true,list);
		}else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
		}
	}
	
	@RequestMapping(value="/addproductcategorys",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		Shop shop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory productCategory : productCategoryList) {
			productCategory.setShopId(shop.getShopId());
			productCategory.setCreateTime(new Date());
		}
		if(productCategoryList!=null&&productCategoryList.size()>0) {
			try {
				ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
				if(productCategoryExecution.getState()== ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", productCategoryExecution.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("success", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品分类必须输入一个");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
	@ResponseBody
	private  Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(productCategoryId!=null&&productCategoryId>0) {
			try {
				Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				
				if(ProductCategoryStateEnum.SUCCESS.getState()==pe.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选中一个商品类别");
		}
		return modelMap;
	}
}
