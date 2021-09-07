package com.proj.animore.form;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
	
	private List<MultipartFile> files; //첨부파일
	}

