package com.ljt.o2o.web.shopadmin;

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
import com.ljt.o2o.dto.ImgHolder;
import com.ljt.o2o.dto.ProductExecution;
import com.ljt.o2o.entity.Product;
import com.ljt.o2o.entity.Shop;
import com.ljt.o2o.enums.ProductStateEnum;
import com.ljt.o2o.exception.ProductOperationException;
import com.ljt.o2o.service.ProductService;
import com.ljt.o2o.util.CodeUtil;
import com.ljt.o2o.util.HttpservletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

	@Autowired 
	private ProductService productService;
	
	//支持上传商品详细图的最大数量
	private static final int IMAGEMAXCOUNT=6;
	
	
	@RequestMapping(value="/addproduct",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		//验证码校验
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpservletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		ImgHolder thumbnail = null;
		List<ImgHolder> productImgList = new ArrayList<ImgHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try {
			//若请求中存在文件流，则取出相关的文件(包括缩略图和详细图)
			if(multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				//取出缩略图并构建ImgHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest;
				thumbnail = new ImgHolder(thumbnailFile.getInputStream(),thumbnailFile.getOriginalFilename());
				for(int i=0;i<IMAGEMAXCOUNT;i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest;
					if(productImgFile!=null) {
						//获取出的第i个详细图片文件流不为空，则将其加入详情图列表
						ImgHolder prodcuImg = new ImgHolder(productImgFile.getInputStream(),productImgFile.getOriginalFilename());
						productImgList.add(prodcuImg);
					}else {
						//若取出的第i个详情图片文件流为空,则终止循环
						break;
					}
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			//尝试获取前端传过来的表单string流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//若product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
		if(product!=null&&thumbnail!=null&&productImgList.size()>0) {
			try {
				//从session中获取当前店铺的Id并赋值给product,减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//执行添加操作
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if(pe.getState()==ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品的信息");
		}
		return modelMap;
	}
	
}
