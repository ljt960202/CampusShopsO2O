package com.ljt.o2o.service;

import java.util.List;

import com.ljt.o2o.entity.HeadLine;

public interface HeadLineService {

	
	List<HeadLine> queryHeadList(HeadLine headLineCondition);
}
