package com.proj.animore.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 댓글목록 요청시 DAO에서 뷰까지 데이터 운반하는 객체
 * @author mypc
 *
 */
@Data
public class RboardListReqDTO {
	int rnum;					//댓글번호
	String nickname;	//회원닉네임(쿼리요청시 조인 사용해서 함께 받아옴)
	String id;				//회원아이디
	String rcontent;	//댓글내용
	int rgroup;				//댓글그룹
	int rstep;				//댓글단계
	int rgood;				//좋아요수
	LocalDateTime rcdate;			//댓글작성일
}
