package com.proj.animore.form;

import java.time.LocalDate;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MyEditForm {
	
	  private String id;
	  private String pw;
	  private String name;
	  private String nickname;
	  private LocalDate birth;
	  private String gender;
	  //TODO
	  private String tel;
	  private String email;
	  private String address;
	  private String mtype;

}
