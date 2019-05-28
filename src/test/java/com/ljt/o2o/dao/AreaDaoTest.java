package com.ljt.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ljt.o2o.BaseTest;
import com.ljt.o2o.entity.Area;

public class AreaDaoTest extends BaseTest{

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testQueryDao() {
		List<Area> list = areaDao.queryArea();
		assertEquals(4, list.size());
	}
}
