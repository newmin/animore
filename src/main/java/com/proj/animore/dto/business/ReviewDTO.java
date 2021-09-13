package com.proj.animore.dto.business;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ReviewDTO {

	private Integer rnum;
	private Integer bnum;
	private Integer rscore;
	private String rcontent;
	private String id;
	private LocalDate rvcdate;
	private LocalDate rvudate;
	
	private List<BusiUploadFileDTO> files;
}
