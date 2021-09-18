package com.proj.animore.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReviewForm {
	private Integer rnum;		
	
	private Integer bnum;		//업체번호
	private String rcontent;	//리뷰내용
	private Integer rscore;		//평점
	private String id;			//작성자ID
	
	private List<MultipartFile> files; //리뷰 첨부 파일
}
