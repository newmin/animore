package com.proj.animore.form;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class JoinMemberForm {
  
//  @NotBlank
//  @Email
//  @Size(max=40)
  private String id;
//  @NotBlank
//  @Pattern(regexp ="^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{9,12}$",
//  message= "영문,숫자,특수문자 조합으로 9~12자리로 설정하시오")
  // @Size(min=8, max=16)
  private String pw;
//   @NotBlank
  private String pw2;
  // TODO private 프로필사진타입 image;
  private MultipartFile image;
//  @NotBlank
  // 이름도 글자수제한 둬야할까요?
//  				-> DB에서 varchar2(9) = 글자수 3자 제한 이런식으로 생각하시면 될 것 같아요
  private String name;
//   @NotBlank
//   @Past
  // past 14년 전 해보고 싶은데 방법 아시는 분
  private String birth;
  //private Date birth;
//   @NotBlank
  private Gender gender;
  //@NotBlank
  private String nickname;
//   @NotBlank
  private String tel;
//   @NotBlank
  private String email;
//   @NotBlank
  private String address;
}
