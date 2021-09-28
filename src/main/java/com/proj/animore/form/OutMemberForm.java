package com.proj.animore.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class OutMemberForm {
	@NotBlank
	private String pwChk;
}
