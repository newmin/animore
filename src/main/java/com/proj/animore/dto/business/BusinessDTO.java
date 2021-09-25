package com.proj.animore.dto.business;

import java.util.List;

import lombok.Data;

@Data
public class BusinessDTO {
	private int bnum;
	private String bbnum;
	private String id;
	private String bname;
	private String baddress;
	private String btel;
	private String btel2;
	private String btel3;
	private String openhours;
	private String nightcare;
	private String rareani;
	private String visitcare;
	private String holidayopen;
	private String dental;
	
	List<BusiUploadFileDTO> files;
	
}