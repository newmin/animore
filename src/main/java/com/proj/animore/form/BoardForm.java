package com.proj.animore.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardForm {

	private int bnum;
	private String id;
	@NotBlank(message = "카테고리를 선택해주세요.")
	private String bcategory;
	@NotBlank(message = "제목을 입력해주세요.")
	private String btitle;
	@NotBlank(message = "내용을 입력해주세요.")
	private String bcontent;
	private int bhit;
	private int bgood;
	private int breply;
	private int bgroup;
}
