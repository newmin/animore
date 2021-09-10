package com.proj.animore.dto.business;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FavoriteReq {

	private Integer fnum;
	private String bname;
	private String id;
	private Integer count;
	private float bscore;
	private LocalDate fdate;
}
