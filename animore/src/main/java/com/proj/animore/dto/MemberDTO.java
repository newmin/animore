package com.proj.animore.dto;

import java.util.Date;

import com.proj.animore.form.Gender;

import lombok.Data;

@Data
public class MemberDTO {
  private String id;
  private String pw;
  private String pw2;
  // TODO private 프로필사진타입 image;
  private String name;
  private String nickname;
  private Date birth;
  private Gender gender;
  private String tel;
  private String email;
  private String address;
  private String mtype;
}
