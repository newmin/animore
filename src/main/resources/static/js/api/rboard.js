/**
 * 게시판 댓글 CRUD에 사용되는 스크립트
 * ajaxCall.js 로드 필요
 * defer 속성 필수
 */
'use strict';

const $rTC= document.querySelector('div.boardForm__replyTextContainer');
const $bnum= document.querySelector('div.boardForm').dataset.bnum;	//게시글번호
const $id= document.getElementById('id');			//회원아이디
//const $rnum= $rTC.dataset.rnum;	//댓글번호
//const $rgroup= $rTC.dataset.rgroup;	//댓글그룹
//const $rstep= $rTC.dataset.rstep;		//댓글단계
const $rcontent= document.querySelector('textarea.boardForm__AddReplyContent');


const addBtn = document.querySelector('button.boardForm__AddReplyBtn');

/* 답댓글은 등록 메소드 따로 만드는게 나을듯? */
/* 신듀댓글등록 */
const addBtn_f = e =>{
	console.log('addBtn_f');
	
	const URL = `/rboard/${$bnum}/${$id}`;
	const data = {
//								 "rnum":$rnum,
								 "bnum":$bnum,
								 "id":$id,
								 "rcontent":$rcontent.value,
//								 "rgroup":$rgroup,
//								 "rstep":$rstep
							 };
	
	request.post(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				const data = res.data;
				
				let html = '';
				data.forEach(rec => {
							html += `<div class="boardForm__replyContainer">`
				      html += `<div class="boardForm__replyImgWrap"><img src="https://picsum.photos/seed/picsum/50/50" alt="" class="boardForm__proImg"></div>`;
				      html += `<div class="boardForm__replyTextContainer" data-rnum="${rec.rnum}" data-rgroup="${rec.rgroup}" data-rstep="${rec.rstep}">`;
				      html += `  <div>`;
				      html += `    <div class="boardForm__ReplyNickname">${rec.nickname}</div>`;
				      html += `    <div class="boardForm__ReplyContent">${rec.rcontent}</div>`;
				      html += `    <div class="boardForm__Replywrap">`;
				      html += `        <div class="boardForm__Replycdate">${rec.rcdate}</div>`;
				      html += `        <button class="boardForm__ReplyReBtn">답글쓰기</button>`;
				      html += `    </div>`;
				      html += `  </div>`;
				      html += `</div>`;
				      html += `</div>`;
				  });
				document.querySelector('.boardForm__replyListWrap').innerHTML = html;
				
				$rcontent.value="";
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			console.log(err.message);
		});
};

/* 댓글수정처리 */
const modiBtn_f = e =>{
	console.log('modiBtn_f');
	
//	const URL = `/rboard/${$bnum}/${$rnum}/${$id}`;
//	const data = { "id":$id.value, "pw":$pw.value, "name":$name.value };

	const URL = '/rboard';
	const data = {
								 "rnum":$rnum,
								 "bnum":$bnum,
								 "id":$id,
								 "rcontent":$rcontent.value,
								 "rgroup":$rgroup,
								 "rstep":$rstep
							 };
	
	request.patch(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				console.log(res);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			errmsg.textContent = err.message;
		});
};

/* */
const findBtn_f = e =>{
	console.log('findBtn_f');
	
//	const URL = `/rboard/${$rnum}`;

	const URL = `/rboard/`;
	const data = {
									 "rnum":$rnum.value,
									 "bnum":$bnum.value,
									 "id":$id.value,
									 "rcontent":$rcontent.innerText,
									 "rgroup":$rgroup.value,
									 "rstep":$rstep.value
								 };
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		errmsg.textContent = err.message;
	});
};

/* 댓글삭제처리 */
const delBtn_f = e =>{
	console.log('delBtn_f');
	
	const URL = `/rboard/${$bnum}/${$rnum}/${$id}`;
	
	request.delete(URL)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				console.log(res);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			errmsg.textContent = err.message;
		});
	};

/* 댓글목록 */
const allBtn_f = e =>{
	console.log('allBtn_f');
	
	const URL = `/rboard/${$bnum}`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		errmsg.textContent = err.message;
	});
};

addBtn.addEventListener("click",addBtn_f);
//modiBtn.addEventListener("click",modiBtn_f);
//findBtn.addEventListener("click",findBtn_f);
//delBtn.addEventListener("click",delBtn_f);
//allBtn.addEventListener("click",allBtn_f);
//clearBtn.addEventListener("click",clearBtn_f);
