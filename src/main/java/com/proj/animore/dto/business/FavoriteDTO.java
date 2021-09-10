package com.proj.animore.dto.business;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FavoriteDTO {

	private int fnum;
 	private int bnum;
 	private String id;
 	private LocalDate fdate;
}
