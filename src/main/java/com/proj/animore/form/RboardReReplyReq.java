package com.proj.animore.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RboardReReplyReq {
	private Integer prnum;
	@NotNull
	private Integer bnum;
	@NotBlank
	private String rcontent;
}
