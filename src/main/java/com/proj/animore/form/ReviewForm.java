package com.proj.animore.form;

import lombok.Data;

@Data
public class ReviewForm {

	private Integer bnum;		//업체번호
	private String rcontent;	//리뷰내용
	private Integer rscore;		//평점
	private String id;			//작성자ID
}
