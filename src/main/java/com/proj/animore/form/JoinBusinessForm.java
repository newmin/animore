package com.proj.animore.form;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class JoinBusinessForm {
	private int bnum;
	@NotBlank
	@Size(max=20)
	private String bbnum;
	@NotBlank
	@Email
	@Size(max=40)
	private String id;
	@NotBlank
	private String bname;
	@NotBlank
	private String baddress;
	@NotBlank
	private String btel;
	@NotBlank
	private String btel2;
	@NotBlank
	private String btel3;
	private String nitghtcare;
	private String rareani;
	private String visitcare;
	private String holidayopen;
	private String dental;
	private String openhours;
	
	private List<MultipartFile> files;
}

