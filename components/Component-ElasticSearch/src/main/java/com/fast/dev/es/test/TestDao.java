package com.fast.dev.es.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.es.dao.ESDao;
import com.fast.dev.es.helper.ESHelper;

@Component
public class TestDao {

	private ESDao esDao = null;

	@Autowired
	private ESHelper eSHelper;

	@Autowired
	private void init() {
		this.esDao = eSHelper.dao("test", "test");
		testAdd();
	}

	private void testAdd() {
		List<Map<String, ?>> mList = new ArrayList<>();
		
	}

}
