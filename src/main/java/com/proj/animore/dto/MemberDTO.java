package com.proj.animore.dto;



import java.time.LocalDate;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberDTO {
  private String id;
  private String pw;
  private String pw2;
  // TODO private 프로필사진타입 image;
  private String name;
  private String nickname;
  @DateTimeFormat(pattern ="yyyy-MM-dd")
  @Past
  private LocalDate birth;
  private String gender;
  //TODO
  private String tel;
  private String email;
  private String address;
  private String mtype;
}
