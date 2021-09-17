package com.proj.animore.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.proj.animore.dto.UpLoadFileDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ProfileForm {
	
	String rid;
	String nickname;
	private UpLoadFileDTO savedImgFile;
	private MultipartFile file;
	
}
