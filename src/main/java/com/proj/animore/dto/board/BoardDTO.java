package com.proj.animore.dto.board;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardDTO {
	
	private Integer bnum;
	private String bcategory;
	private String btitle;
	private String id;
	private int bhit;
	private int bgood;
	private int breply;
	private String bcontent;
	private int bgroup;
  private LocalDateTime bcdate;
  private LocalDateTime budate;


  	private List<BoardUploadFileDTO> files;
	//private List<MultipartFile> files; //첨부파일
}

