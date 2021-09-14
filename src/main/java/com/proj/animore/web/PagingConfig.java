package com.proj.animore.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proj.animore.common.paging.FindCriteria;
import com.proj.animore.common.paging.PageCriteria;
import com.proj.animore.common.paging.RecordCriteria;

@Configuration
public class PagingConfig {
	static final int REC10 = 10;  //한페이지에 보여줄 레코드수
	static final int PAGE10 = 10;	//한페이지에 보여줄 페이지수
	
	static final int REC5 = 5;  //한페이지에 보여줄 레코드수
	static final int PAGE5 = 5;	//한페이지에 보여줄 페이지수
	
	static final int REC8 = 8;  //한페이지에 보여줄 레코드수

	
	@Bean(name = "rec10")
	public RecordCriteria rc10() {
		return new RecordCriteria(REC10);
	}
	@Bean(name = "rec8")
	public RecordCriteria rc8() {
		return new RecordCriteria(REC8);
	}
	
	@Bean(name = "pc10_2")
	public PageCriteria pc10_2() {
		return new PageCriteria(rc8(), PAGE10);
	}
	
	@Bean(name = "pc10")
	public PageCriteria pc10() {
		return new PageCriteria(rc10(), PAGE10);
	}
	@Bean(name = "rec5")
	public RecordCriteria rc5() {
		return new RecordCriteria(REC5);
	}
	@Bean(name = "pc5")
	public PageCriteria pc5() {
		return new PageCriteria(rc5(), PAGE5);
	}
	@Bean
	public FindCriteria fc10() {
		return new FindCriteria(rc10(),PAGE10);
	}
	@Bean
	public FindCriteria fc8() {
		return new FindCriteria(rc8(),PAGE10);
	}
}
