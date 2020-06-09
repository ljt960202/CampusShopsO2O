package com.ljt.o2o.service;

import java.util.List;

import com.ljt.o2o.entity.HeadLine;

public interface HeadLineService {
	public static final String HEADLINELISTKEY = "headlinelist";
	
	List<HeadLine> queryHeadList(HeadLine headLineCondition);
}
