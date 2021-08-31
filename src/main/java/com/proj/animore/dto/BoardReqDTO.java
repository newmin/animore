package com.proj.animore.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
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
	private int bgroup;
  private LocalDate bcdate;
  private LocalDate budate;
}
