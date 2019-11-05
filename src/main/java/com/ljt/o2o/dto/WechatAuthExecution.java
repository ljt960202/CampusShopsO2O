package com.ljt.o2o.dto;

import java.util.List;

import com.ljt.o2o.entity.WechatAuth;
import com.ljt.o2o.enums.WechatAuthStateEnum;

public class WechatAuthExecution {
	//结果标识
	private int state;
	//状态信息
	private String stateInfo;
	//数量
	private int count;
	//操作的wechatAuth(增删改查时用得到)
	private WechatAuth wechatAuth;
	//wechatAuth列(查询列表的时候使用)
	private List<WechatAuth> wechatAuthList;
	
	public WechatAuthExecution(){
		
	}
	
	//操作失败的时候使用的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功的时候使用的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,WechatAuth wechatAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuth = wechatAuth;
	}
	
	//操作成功的时候使用的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,List<WechatAuth> wechatAuthList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuthList = wechatAuthList;
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

	public WechatAuth getWechatAuth() {
		return wechatAuth;
	}

	public void setWechatAuth(WechatAuth wechatAuth) {
		this.wechatAuth = wechatAuth;
	}

	public List<WechatAuth> getWechatAuthList() {
		return wechatAuthList;
	}

	public void setWechatAuthList(List<WechatAuth> wechatAuthList) {
		this.wechatAuthList = wechatAuthList;
	}

	

}
