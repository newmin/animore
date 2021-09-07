package com.proj.animore.dto.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardUploadFileDTO {
		private Integer fnum; 						//파일번호
	  private Integer bnum; 					//게시글번호
	  private String store_fname; 	//서버보관 파일명 store_fname varchar2(50),
	  private String upload_fname; 	//업로드 파일명 upload_fname varchar2(50),
	  private String fsize;					//파일 크기 fsize varchar2(45),
	  private String ftype;					//파일 유형 ftype varchar2(50),
	  private LocalDateTime cdate; 	//생성일시 timestamp default systimestamp,
	  private LocalDateTime udate; 	// 수정일시 udate timestamp,
}
