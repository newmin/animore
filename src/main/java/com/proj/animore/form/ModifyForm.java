package com.proj.animore.form;

import java.time.LocalDate;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ModifyForm {
	
	  private String id;
	  private String pw;
	  // TODO private 프로필사진타입 image;
	  private String name;
	  private String nickname;
	  @DateTimeFormat(pattern ="yyyy-MM-dd")
	  @Past
	  private LocalDate birth;
	  private String gender;
	  private String tel;
	  private String email;
	  private String address;
	  private String mtype;
}
