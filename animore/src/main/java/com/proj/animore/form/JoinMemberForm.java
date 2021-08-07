package com.proj.animore.form;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class JoinMemberForm {
  
  @NotBlank
  // @Email
  @Size(max=40)
  private String id;
  @NotBlank
  // @Size(min=8, max=16)
  private String pw;
  // @NotBlank
  // pw와 일치검사?
  private String pw2;
  // TODO private 프로필사진타입 image;
  @NotBlank
  // 이름도 글자수제한 둬야할까요?
  private String name;
  // @NotBlank
  // @Past
  // past 14년 전 해보고 싶은데 방법 아시는 분
  private Date birth;
  // @NotBlank
  private Gender gender;
  // @NotBlank
  private String tel;
  // @NotBlank
  private String email;
  // @NotBlank
  private String address;
}
