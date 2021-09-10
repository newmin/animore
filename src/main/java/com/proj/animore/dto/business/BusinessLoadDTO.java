package com.proj.animore.dto.business;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BusinessLoadDTO {
	private int bnum;
	private Float bscore;
	private String bname;
	private String baddress;
	private String btel;
	private String openhours;
	private LocalDate fdate;
	private String id;

	private String bhospital;
	private String bpharmacy;
	private String bhotel;
	private String bkindergarden;
	private String bfood;
	private String btraining;
	private String bshop;
	private String bplayground;
	private String bhairshop;
	private String betc;

	private String nightcare;
	private String rareani;
	private String visitcare;
	private String holidayopen;
	private String dental;
	
	private String search; //검색어

}
