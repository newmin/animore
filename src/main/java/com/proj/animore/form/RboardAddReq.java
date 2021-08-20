package com.proj.animore.form;

import lombok.Data;

/**
 * 댓글 등록시 리퀘스트 바디에 들어가는 정보
 * @author mypc
 *
 */

@Data
public class RboardAddReq {
	private String bnum;			//게시글번호
	private String id;				//작성자id
	private String rgroup;		//댓글그룹
	private String rcontent;	//댓글내용
}
