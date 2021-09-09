package com.proj.animore.dto.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RboardDTO {
	Integer rnum;			//댓글번호
	Integer prnum;		//부모댓글번호
	Integer bnum;			//게시글번호
	String id;				//회원id
	LocalDateTime rcdate;			//작성일자
	String rcontent;	//댓글내용
	Integer rgroup;		//댓글그룹
	Integer rstep;		//댓글단계
	Integer rindent;	//댓글들여쓰기
}
