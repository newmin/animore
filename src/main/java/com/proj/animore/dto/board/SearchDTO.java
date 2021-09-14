package com.proj.animore.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchDTO {

	private String bcategory;
	private int startRec;
	private int endRec;
	private String searchType; //검색유형
	private String keyword;	//검색어
}
