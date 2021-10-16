package com.proj.animore.dto;



import java.time.LocalDate;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.proj.animore.dto.business.BusiUploadFileDTO;

import lombok.Data;

@Data
public class MemberDTO {
  private String id;
  private String pw;
  private String name;
  private String nickname;
  @DateTimeFormat(pattern ="yyyy-MM-dd")
  private LocalDate birth;
  private String gender;
  private String tel;
  private String tel2;
  private String tel3;
  private String email;
  private String address;
  private String mtype;
  private int  mileage;
  private String upload_fname; //사용자가 첨부한 파일명
  private String store_fname;  //DB에 저장될 파일명(중복방지를 위해 임의의 명칭으로 등록)
  private String fsize;		//파일 크기 fsize default puppy
  private String ftype;		//유형 check in('jpg','png','gif','bmp') default png
  private String myAni;
  
}
