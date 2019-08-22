package com.ljt.o2o.dto;

import java.util.List;

import com.ljt.o2o.entity.Product;
import com.ljt.o2o.enums.ProductStateEnum;

public class ProductExecution {
	//结果标识
	private int state;
	//状态信息
	private String stateInfo;
	//商品数量
	private int count;
	//操作的product(增删改查时用得到)
	private Product product;
	//Product列(查询商品列表的时候使用)
	private List<Product> productList;
	
	public ProductExecution(){
		
	}
	
	//商品操作失败的时候使用的构造器
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//商品操作成功的时候使用的构造器
	public ProductExecution(ProductStateEnum stateEnum,Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}
	
	//商品操作成功的时候使用的构造器
	public ProductExecution(ProductStateEnum stateEnum,List<Product> productList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productList = productList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}


}
