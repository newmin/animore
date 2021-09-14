/**
 * 게시판 댓글 CRUD에 사용되는 스크립트
 * ajaxCall.js 로드 필요
 * defer 속성 필수
 */
'use strict';

//타게팅

const $bnum= document.querySelector('div.boardForm').dataset.bnum;	//게시글번호
const $id= document.querySelector('li[data-id]').dataset.id;			//회원아이디

const $rcontent= document.querySelector('textarea.boardForm__AddReplyContent'); //댓글입력텍스트상자
const $boardForm__reply = document.querySelector('.boardForm__reply');

//버튼 타게팅
const addBtn = document.querySelector('button.boardForm__AddReplyBtn');	//댓글등록
let replyReBtns = document.querySelectorAll('button.boardForm__ReplyReBtn');		//대댓글달기
let modiBtns = document.querySelectorAll('button.boardForm__modiReplyBtn');	//댓글수정
let delBtns = document.querySelectorAll('button.boardForm__delReplyBtn');		//댓글삭제

/* 신규댓글등록 */
const addBtn_f = e =>{

	//댓글입력체크
	if(!$rcontent.value) {
		alert("댓글 내용을 입력하세요");
		return;
	}
	
	const URL = `/rboard/${$bnum}`;
	const data = {
								 "bnum":$bnum,
								 "rcontent":$rcontent.value,
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

	const $rnum = e.target.dataset.rnum;
	const $reRcontent = boardForm__reReplyTextarea.value;
	
	//댓글입력체크
	if(!$reRcontent) {
		alert("댓글 내용을 입력하세요");
		return;
	}
	
	const URL = `/rboard/${$bnum}/${$rnum}`;
	const data = {
								 "bnum":$bnum,
								 "rnum":$rnum,
								 "rcontent":$reRcontent
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

	const $rnum = e.target.dataset.rnum;
	const $rcontent_modi = document.querySelector("textarea#boardForm__modiReplyTextarea");
	
	const URL = `/rboard/${$bnum}/${$rnum}`;
	const data = {
								 "rnum":$rnum,
								 "bnum":$bnum,
								 "rcontent":$rcontent_modi.value
							 };
	
	request.patch(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				const data = res.data;
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

/* 댓글삭제처리 */
const delBtn_f = e =>{

	if(!confirm('정말 삭제하시겠습니까?'))	return;
	const $rnum = e.target.dataset.rnum;
	
	const URL = `/rboard/${$bnum}/${$rnum}`;
	
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

addBtn.addEventListener("click",addBtn_f);

Array.from(delBtns).forEach(ele => {
  ele.addEventListener("click",delBtn_f);
});

/* 댓글목록갱신 */
function refreshReply(data){
	let html = '';
	data.forEach(rec => {
		const $rcdate = getTimeStamp(rec.rcdate);
		
//		const $rcdate = rec.rcdate; 
//		const ymd = $rcdate.substring(0,$rcdate.indexOf('T'));
//		const hms = $rcdate.substring($rcdate.indexOf('T')+1,$rcdate.indexOf('.'));
					
					html += `<div class="boardForm__replyContainer">`
			if(rec.rindent > 0){
				for(let i=0; i<rec.rindent; i++){
					html += `<div style="width:50px"></div>`;
				}
			}
		      html += `<div class="boardForm__replyImgWrap"><img src="/img/upload/member/${rec.store_fname}" alt="" class="boardForm__proImg"></div>`;
		      html += `	<div class="boardForm__replyTextContainer" data-rnum="${rec.rnum}">`;
		      html += `  <div>`;
		      html += `    <div class="boardForm__ReplyNickname">${rec.nickname}</div>`;
		      html += `    <div class="boardForm__ReplyContent">${rec.rcontent}</div>`;
		      html += `    <div class="boardForm__Replywrap">`;
					if(rec.status == 'A'){
//		      		html += `        <div class="boardForm__Replycdate">${ymd} ${hms}</div>`;
//		      		html += `        <div class="boardForm__Replycdate">${rec.rcdate}</div>`;
		      		html += `        <div class="boardForm__Replycdate">${$rcdate}</div>`;
							html += `        <button class="boardForm__ReplyReBtn" data-rnum="${rec.rnum}">답글</button>`;
							if($id == rec.id){
									html += `<button class="boardForm__modiReplyBtn" data-rnum="${rec.rnum}">수정</button>`;
									html += `<button class="boardForm__delReplyBtn" data-rnum="${rec.rnum}">삭제</button>`;
							}
					}
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
	  ele.addEventListener("click",reReTextbox_f);
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

function getTimeStamp(rcdate) {
  var d = new Date(rcdate);
  var s =
    leadingZeros(d.getFullYear(), 4) + '-' +
    leadingZeros(d.getMonth() + 1, 2) + '-' +
    leadingZeros(d.getDate(), 2) + ' ' +

    leadingZeros(d.getHours(), 2) + ':' +
    leadingZeros(d.getMinutes(), 2) + ':' +
    leadingZeros(d.getSeconds(), 2);

  return s;
}

function leadingZeros(n, digits) {
  var zero = '';
  n = n.toString();

  if (n.length < digits) {
    for (let i = 0; i < digits - n.length; i++)
      zero += '0';
  }
  return zero + n;
}