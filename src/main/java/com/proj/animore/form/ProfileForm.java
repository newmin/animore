package com.proj.animore.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ProfileForm {
	
	String id;
	String nickname;
	private List<MultipartFile> files; //첨부파일

}
