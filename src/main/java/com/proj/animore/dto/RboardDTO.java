package com.proj.animore.dto;

import lombok.Data;

@Data
public class RboardDTO {
	int rnum;			//댓글번호
	int bnum;			//게시글번호
	String id;				//회원id
	String rcontent;	//댓글내용
	String rgroup;		//댓글그룹
	String rstep;			//댓글단계
}
