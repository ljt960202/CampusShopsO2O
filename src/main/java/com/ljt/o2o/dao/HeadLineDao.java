package com.ljt.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ljt.o2o.entity.HeadLine;

public interface HeadLineDao {
	public List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLine);
}
