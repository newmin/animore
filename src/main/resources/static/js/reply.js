/**
 * 답글쓰기, 댓글수정 기능과 관련된 스크립트
 */
 'use strict';
	
// 댓글수정버튼 누르면 기존 div 숨기고 그 자리에 댓글 수정을 위한 div 생성해서 보이기
const modiTextbox_f = e => {

	if(document.querySelector('div.boardForm__modiReplyTextarea')) {
		document.querySelector('div.boardForm__modiReplyTextarea').previousElementSibling.classList.toggle('boardForm__hideReply');
		document.querySelector('div.boardForm__modiReplyTextarea').remove();
	}
	
	//댓글번호
	const $rnum = e.target.dataset.rnum;
	console.log("modiTextbox_f");
	console.log(e);
	console.log($rnum);
	
	//상위요소 더 깔끔하게 타게팅 하는거 찾아보기(배운거같음)
	//버튼누르면 기존댓글 div에 댓글숨기기 클래스 토글(숨김)
	const $boardForm__replyTextContainer = e.target.closest('.boardForm__replyTextContainer');
	//수정전댓글내용
	const $boardForm__ReplyContent = e.target.parentElement.previousElementSibling
	
	$boardForm__replyTextContainer.classList.toggle('boardForm__hideReply');

	let $boardForm__modiReplyTextarea = document.createElement("div");
	$boardForm__modiReplyTextarea.classList.add("boardForm__modiReplyTextarea");
	
	//댓글수정 textbox생성
	let html = `<div id="boardForm__modiReplyWrap">`;
			html +=`	<textarea name="rcontent_modi" cols="30" rows="3" id="boardForm__modiReplyTextarea" style="resize: none;"></textarea>`;
			html +=`	<button id="boardForm__modiReplyCancelBtn">취소</button>`
			html +=`	<button data-rnum="${$rnum}" id="boardForm__modiReplySubmitBtn">등록</button>`
			html +=`</div>`
	$boardForm__modiReplyTextarea.innerHTML = html;
	
	//숨긴 댓글 다음요소로 textbox 나타나게 하기
	$boardForm__replyTextContainer.after($boardForm__modiReplyTextarea);
	//textbox 안에 수정전 댓글내용 보이게하기
	document.querySelector("textarea[name=rcontent_modi]").textContent=$boardForm__ReplyContent.textContent;
	
	//버튼에 이벤트 달아주기
	//취소버튼 - 텍스트상자 제거하기, 원래댓글 보여주기
	boardForm__modiReplyCancelBtn.addEventListener("click",e=>{
		console.log("modiReplyCancelBtn");
		$boardForm__modiReplyTextarea.remove();
		$boardForm__replyTextContainer.classList.toggle('boardForm__hideReply');
	});
	
	//등록버튼 - 댓글수정처리 이벤트 추가
	boardForm__modiReplySubmitBtn.addEventListener("click",modiBtn_f);
	
}


// 대댓글쓰기 버튼 누르면 대댓글 작성을 위한 div 생성해서 보이기 
const reReTextbox_f = e => {
	const $rnum = e.target.dataset.rnum;

	//대댓글 달기 textbox 생성
	let $boardForm__reReplyTextarea = document.createElement("div");
	$boardForm__reReplyTextarea.classList.add("boardForm__reReplyTextarea");

	let html = `<div id="boardForm__reReplyWrap">`;
			html +=`	<textarea name="rcontent_reReply" cols="30" rows="3" id="boardForm__reReplyTextarea" style="resize: none;"></textarea>`;
			html +=`	<button id="boardForm__reReplyCancelBtn">취소</button>`
			html +=`	<button data-rnum="${$rnum}" id="boardForm__reReplySubmitBtn">등록</button>`
			html +=`</div>`
	$boardForm__reReplyTextarea.innerHTML = html;

	//대댓글 붙일 댓글 밑에 대댓글 입력상자 나타나게 하기
	e.target.closest('.boardForm__replyContainer').after($boardForm__reReplyTextarea);

	//버튼에 이벤트 달아주기
	//취소버튼 - 텍스트상자 제거하기
	boardForm__reReplyCancelBtn.addEventListener("click",e=>{
		console.log("reReplyCancelBtn");
		$boardForm__reReplyTextarea.remove();
	});
	
	//등록버튼 - 댓글수정처리 이벤트 추가
	boardForm__reReplySubmitBtn.addEventListener("click",replyReBtn_f);

}


Array.from(replyReBtns).forEach(ele => {
	ele.addEventListener("click",reReTextbox_f);
});
modiBtns = document.querySelectorAll('button.boardForm__modiReplyBtn');
Array.from(modiBtns).forEach(ele => {
	ele.addEventListener("click", modiTextbox_f);
});
	