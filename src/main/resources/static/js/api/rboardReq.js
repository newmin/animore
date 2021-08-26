/**
 * 게시판 댓글 CRUD에 사용되는 스크립트
 * ajaxCall.js 로드 필요
 * defer 속성 필수
 */
'use strict';

//타게팅
//const $rTC= document.querySelector('div.boardForm__replyTextContainer');
//const $rnum= $rTC.dataset.rnum;	//댓글번호
//const $rgroup= $rTC.dataset.rgroup;	//댓글그룹
//const $rstep= $rTC.dataset.rstep;		//댓글단계

const $bnum= document.querySelector('div.boardForm').dataset.bnum;	//게시글번호
const $id= document.querySelector('li[data-id]').dataset.id;			//회원아이디

const $rcontent= document.querySelector('textarea.boardForm__AddReplyContent'); //댓글입력텍스트상자
const $boardForm__reply = document.querySelector('.boardForm__reply');

//버튼들
const addBtn = document.querySelector('button.boardForm__AddReplyBtn');	//댓글등록
let replyReBtns = document.querySelectorAll('button.boardForm__ReplyReBtn');		//대댓글달기
let modiBtns = document.querySelectorAll('button.boardForm__modiReplyBtn');	//댓글수정
let delBtns = document.querySelectorAll('button.boardForm__delReplyBtn');		//댓글삭제

/* 답댓글은 등록 메소드 따로 만드는게 나을듯? */
/* 신규댓글등록 */
const addBtn_f = e =>{
	console.log('addBtn_f');
	
	//댓글입력체크
	if(!$rcontent.value) {
		alert("댓글 내용을 입력하세요");
		return;
	}
	
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
				//댓글목록갱신
				refreshReply(data);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			console.log(err.message);
			alert(err.message);
		});
};

/* 대댓글 등록 */
const replyReBtn_f = e =>{
	console.log('replyReBtn_f');
	
	//댓글입력체크
	if(!$reRcontent.value) {
		alert("댓글 내용을 입력하세요");
		return;
	}
	
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
				//댓글목록갱신
				refreshReply(data);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			console.log(err.message);
			alert(err.message);
		});
};


/* 댓글수정처리 */
const modiBtn_f = e =>{
	console.log('modiBtn_f');
	const $rnum = e.target.dataset.rnum;
	const $rcontent_modi = document.querySelector("textarea#boardForm__modiReplyTextarea");
	
	const URL = `/rboard/${$bnum}/${$rnum}/${$id}`;
	const data = {
								 "rnum":$rnum,
								 "bnum":$bnum,
								 "id":$id,
								 "rcontent":$rcontent_modi.value,
//								 "rgroup":$rgroup,
//								 "rstep":$rstep
							 };
	
	request.patch(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				const data = res.data;
				//$boardForm__modiReplyTextarea.;	//수정로직 마치고 텍스트상자 제거(없어도 될듯한데 일단 넣어봤음)
				refreshReply(data);				//댓글목록갱신
				
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			alert(err.message);
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
	console.log(e.target);
	const $rnum = e.target.dataset.rnum;
	
	const URL = `/rboard/${$bnum}/${$rnum}/${$id}`;
	
	request.delete(URL)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				const data = res.data;
				//댓글목록갱신
				refreshReply(data);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			console.log(err.message);
			alert(err.message);
	});
};
/*
Array.from(modiBtns).forEach(ele => {
  ele.addEventListener("click", e=>{
    console.log('modiBtn_f');
    console.log(e.parentElement);
    const $rnum=e.dataset.rnum;
    
    const URL = `/rboard/${$bnum}/${$rnum}/${$id}`;
    
    request.delete(URL)
      .then(res=>res.json())
      .then(res=>{
        if(res.rtcd == '00'){
          //성공로직처리
          const data = res.data;
          //댓글목록갱신
          refreshReply(data);
        }else{
          throw new Error(res.rtmsg);
        }
      })
      .catch(err=>{
        //오류로직 처리
        errmsg.textContent = err.message;
		});
  });
});
*/

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

Array.from(replyReBtns).forEach(ele => {
  ele.addEventListener("click",replyReBtn_f);
});
Array.from(modiBtns).forEach(ele => {
  ele.addEventListener("click",modiBtn_f);
});
Array.from(delBtns).forEach(ele => {
  ele.addEventListener("click",delBtn_f);
});

//findBtn.addEventListener("click",findBtn_f);
//delBtn.addEventListener("click",delBtn_f);
//allBtn.addEventListener("click",allBtn_f);
//clearBtn.addEventListener("click",clearBtn_f);


/* 댓글목록새로고침 */
function refreshReply(data){
	let html = '';
	data.forEach(rec => {
					html += `<div class="boardForm__replyContainer">`
		      html += `<div class="boardForm__replyImgWrap"><img src="https://picsum.photos/seed/picsum/50/50" alt="" class="boardForm__proImg"></div>`;
		      html += `	<div class="boardForm__replyTextContainer" data-rnum="${rec.rnum}" data-rgroup="${rec.rgroup}" data-rstep="${rec.rstep}">`;
		      html += `  <div>`;
		      html += `    <div class="boardForm__ReplyNickname">${rec.nickname}</div>`;
		      html += `    <div class="boardForm__ReplyContent">${rec.rcontent}</div>`;
		      html += `    <div class="boardForm__Replywrap">`;
		      html += `        <div class="boardForm__Replycdate">${rec.rcdate}</div>`;
		      html += `        <button class="boardForm__ReplyReBtn">답글쓰기</button>`;
	if($id == rec.id){	html += `<button class="boardForm__modiReplyBtn" data-rnum="${rec.rnum}" data-rgroup="${rec.rgroup}" data-rstep="${rec.rstep}">댓글수정</button>`; }
	if($id == rec.id){	html += `<button class="boardForm__delReplyBtn" data-rnum="${rec.rnum}" data-rgroup="${rec.rgroup}" data-rstep="${rec.rstep}">댓글삭제</button>`; }
		      html += `    </div>`;
		      html += `  </div>`;
		      html += `</div>`;
		      html += `</div>`;
			});
	document.querySelector('.boardForm__replyListWrap').innerHTML = html;
	
	$boardForm__reply.innerText=`댓글수 ${data.length}`;

	//댓글목록 갱신하고 생긴 버튼들에 이벤트리스너 다시 달아줌
	//대댓글달기, 수정, 삭제버튼
	replyReBtns = document.querySelectorAll('button.boardForm__ReplyReBtn');
	Array.from(replyReBtns).forEach(ele => {
	  ele.addEventListener("click",replyReBtn_f);
	});
	modiBtns = document.querySelectorAll('button.boardForm__modiReplyBtn');
	Array.from(modiBtns).forEach(ele => {
	  ele.addEventListener("click", modiTextbox_f);
	});
	delBtns = document.querySelectorAll('button.boardForm__delReplyBtn');
	Array.from(delBtns).forEach(ele => {
	  ele.addEventListener("click",delBtn_f);
	});
	
	$rcontent.value="";
	
}
