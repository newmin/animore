package com.proj.animore.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
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
}
