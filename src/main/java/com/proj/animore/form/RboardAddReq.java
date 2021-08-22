package com.proj.animore.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 댓글 등록시 리퀘스트 바디에 들어가는 정보
 * @author mypc
 *
 */

@Data
public class RboardAddReq {
	
	@NotBlank
	private String bnum;			//게시글번호
	private String id;				//작성자id (지금은 세션의 loginMember객체로 읽기때문에 사용되지 않고있음. 추후 사용될 가능성을 염두에 두어 놔둔 상태)
	@NotBlank
	private String rcontent;	//댓글내용
}
