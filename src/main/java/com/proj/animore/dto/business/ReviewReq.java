package com.proj.animore.dto.business;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.proj.animore.form.ReviewFile;

import lombok.Data;

@Data
public class ReviewReq {

	private Integer rnum;			//리뷰번호
	private Integer bnum;			//리뷰가 적힌 업체번호
	private Integer rscore;		//사용자가 입력한 평점
	private String rcontent;	//리뷰내용
	private String id;				//작성자ID
	private String rvReply; 	//리뷰에 대한 사장님 댓글
	private String bname;
	private String nickname;
	private LocalDate rvcdate;
	private LocalDate rvudate;
	private String store_fname; //프로필 사진명
	private List<BusiUploadFileDTO> files;
}
