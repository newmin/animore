package com.proj.animore.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PostVO {
  private int bnum;
	private String btitle;
	private String id;
	private int bhit;
	private int bgood;
	private int breply;
	private String bcontent;
  private LocalDate bcdate;
}