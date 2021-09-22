package com.proj.animore.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MypageReplyRes {
	private Integer rnum;
	private Integer bnum;
	private String rcontent;
	private Date rcdate;
	private Integer bgood;
	private int rownum;
}
