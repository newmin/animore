package com.proj.animore.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewReq {

	private Integer rnum;		//리뷰번호
	private Integer bnum;		//리뷰가 적힌 업체번호
	private Integer rscore;		//사용자가 입력한 평점
	private String rcontent;	//리뷰내용
	private String id;			//작성자ID
	private String nickname;
	private LocalDate rvcdate;
	private LocalDate rvudate;
}
