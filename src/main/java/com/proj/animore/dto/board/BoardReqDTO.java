package com.proj.animore.dto.board;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardReqDTO {

	private Integer bnum;
	private String bcategory;
	private String btitle;
	private String id;
	private String nickname;
	private int bhit;
	private int bgood;
	private int breply;
	private String bcontent;
	private int pbnum;
	private int bgroup;
	private int bstep;
	private int bindent;
	private String bstatus;
	private LocalDate bndate;
	private LocalDate bcdate;
	private LocalDate budate;
	private String store_fname;
	
	private List<BoardUploadFileDTO> files;
	}

