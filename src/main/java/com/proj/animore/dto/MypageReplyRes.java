package com.proj.animore.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MypageReplyRes {
	Integer rnum;
	Integer bnum;
	String rcontent;
	Date rcdate;
	Integer bgood;
}
