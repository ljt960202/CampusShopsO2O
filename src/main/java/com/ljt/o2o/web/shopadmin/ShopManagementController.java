package com.ljt.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljt.o2o.dto.ShopExecution;
import com.ljt.o2o.entity.Area;
import com.ljt.o2o.entity.PersonInfo;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.entity.ShopCategory;
import com.ljt.o2o.enums.ShopStateEnum;
import com.ljt.o2o.service.AreaService;
import com.ljt.o2o.service.ShopCategoryService;
import com.ljt.o2o.service.ShopService;
import com.ljt.o2o.util.CodeUtil;
import com.ljt.o2o.util.HttpservletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	
	@RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Long shopId = HttpservletRequestUtil.getLong(request, "shopId");
		if(shopId<=0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if(currentShopObj==null) {
				modelMap.put("redirect",true);
				modelMap.put("url","/o2o/shopadmin/shoplist");
			}else {
				Shop currentShop = (Shop)currentShopObj;
				modelMap.put("redirect",false);
				modelMap.put("shopId",currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			modelMap.put("redirect",false);
			request.getSession().setAttribute("currentShop", currentShop);
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopList(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setName("test");
		request.getSession().setAttribute("user", user);
		user = (PersonInfo)request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
			modelMap.put("success", true);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", user);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getbyshopid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getByShopId(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Long shopId = HttpservletRequestUtil.getLong(request, "shopId");
		if(shopId>-1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}
	
	
	@RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopInitInfo(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/registershop",method = RequestMethod.POST)
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpservletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if( multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2.注册店铺
		if(shop!=null& shopImg!=null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
				if(se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					//该用户可以操作的店铺列表
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if(shopList == null || shopList.size()==0) {
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		//3.返回结果
	}
	
	
	@RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
	private Map<String,Object> modifyShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpservletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if( multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		//2.修改店铺信息
		if(shop!=null& shop.getShopId()!=null) {
			ShopExecution se;
			try {
				if(shopImg==null) {
					se = shopService.modifyShop(shop, null,null);
				}else {
					se = shopService.modifyShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
				}
				if(se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺ID");
			return modelMap;
		}
		//3.返回结果
	}
	
//	private static void inputStreamToFile(InputStream ins,File file) {
//		FileOutputStream fos = null;
//		try {
//			 fos = new FileOutputStream(file);
//			 int bytesRead = 0;
//			 byte[] buffer = new byte[1024];
//			 while ((bytesRead=ins.read(buffer))!=-1) {
//				fos.write(buffer, 0, bytesRead);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException("调用inputStreamToFile产生异常:"+e.getMessage());
//		} finally {
//			try {
//				if(fos!=null) {
//					fos.close();
//				}
//				if(ins!=null) {
//					ins.close();
//				}
//			} catch (IOException e) {
//				throw new RuntimeException("inputStreamToFile关闭IO产生异常:"+e.getMessage());
//			}
//		}
//	}
}