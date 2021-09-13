package com.proj.animore.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RboardModiReq {
	@NotBlank
	private String rnum;
	@NotBlank
	private String bnum;
	@NotBlank
	private String rcontent;
}
