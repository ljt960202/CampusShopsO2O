package com.ljt.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljt.o2o.dao.HeadLineDao;
import com.ljt.o2o.entity.HeadLine;
import com.ljt.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
	private HeadLineDao headLineDao;
	@Override
	public List<HeadLine> queryHeadList(HeadLine headLineCondition) {
		return headLineDao.queryHeadLine(headLineCondition);
	}

}
