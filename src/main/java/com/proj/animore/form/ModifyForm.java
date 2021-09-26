package com.proj.animore.form;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ModifyForm {
	
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String nickname;
	@NotNull
  	@Past
  	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate birth;
	private Integer tel;
	private Integer tel2;
	private Integer tel3;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String address;
	@NotBlank
	private String pwChk;
}
