package com.ljt.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin",method= {RequestMethod.GET})
/**
 * 主要用来解析路由并转发到相应的html中
 * @author Administrator
 *
 */
public class ShopAdminController {
	
	@RequestMapping("/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
	
	@RequestMapping("/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
	
	@RequestMapping("/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}
	
	@RequestMapping("/productcategorymanagement")
	public String productCategoryManagement() {
		return "shop/productcategorymanagement";
	}
	
	@RequestMapping("/productoperation")
	public String productOperation() {
		return "shop/productoperation";
	}
	
	@RequestMapping("/productmanagement")
	public String productManagement() {
		return "shop/productmanagement";
	}
}
