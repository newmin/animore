package com.proj.animore.dao;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ReviewDAOImplTest {

	@Autowired
	private ReviewDAOImpl dao;
	
	void findReview() {
		
		log.info(dao.findReview(5).toString());
	}
	
	
	
	
}
