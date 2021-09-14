package com.proj.animore.dto.board;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 댓글목록 요청시 DAO에서 뷰까지 데이터 운반하는 객체
 * @author mypc
 *
 */
@Data
public class RboardListReqDTO {
	Integer rnum;					//댓글번호
	String nickname;	//회원닉네임(쿼리요청시 조인 사용해서 함께 받아옴)
	String id;				//회원아이디
	String rcontent;	//댓글내용
	String prnum;
	Integer rgroup;				//댓글그룹
	Integer rstep;				//댓글단계
	Integer rindent;			//댓글들여쓰기
	Integer rgood;				//좋아요수
	Timestamp rcdate;			//댓글작성일
	String status;
	String store_fname;
}
