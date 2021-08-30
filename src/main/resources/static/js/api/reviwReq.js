/**
 * 
 */
 'use strict'
 
//  호출하는 값
const $id = 'user@test.com'	//아이디
const bnum = document.querySelector('.selected-busi').dataset.bnum;		//업체번호
//  버튼
const regiBtn = document.querySelector('.review__regist');
const modiBtn = document.querySelectorAll('.review__modi')
const delBtn = document.querySelectorAll('.review__del');
//리뷰 작성폼
const rcontent = document.querySelector('.review__textarea');
// const rscore = document.querySelector('input[name="rscore"]:checked');

regiBtn.addEventListener('click,regiBtn_f')


//  리뷰등록
const regiBtn_f = e =>{
	console.log('regiBtn_f');
	
	//리뷰입력체크
	if(!rcontent.value) {
			alert("리뷰 내용을 입력하세요");
			return;
	}	
	
	const URL = `/inquire/${bnum}`;
	const data = {
			"bnum": bnum,
			"rcontent": rcontent.value,
			"rscore" : document.querySelector('input[name="rscore"]:checked').value,
			"id": $id
													 };
	
	request.post(URL,data)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == '00'){
							//성공로직처리
							const data = res.data;
							//댓글목록갱신
							reloadReview(data);
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





// 리뷰 새로 고침
function reloadReview(data){
	let html ='';
	data.forEach(review=>{
		// html +=`  <div class="review__row" th:each="review :${review}">`;
		html +=`  <div class="review__row">`;
		html +=`		<div class="review__column"><img class="review__profile" src="https://picsum.photos/id/10/50/50" alt="프로필사진"></div>`;
		html +=`		<div class="review__column">`;
		html +=`			<p class="review__nickname" th:text="${review.nickname}">닉네임</p>`;
		html +=`			<div class="review__star-score" th:text=${review.rscore}><i class="fas fa-star"><i class="far fa-star"></i></i></div>`;
		html +=`			<p class="review__date" th:text="|작성일자 : ${review.rvcdate}|">작성일자</p>`;
		html +=`			<p class="review__content" th:text="${review.rcontent}">리뷰내용</p>`;
		html +=`			<div th:if="${session.loginMember.id.equals(review.id)}"><p><a th:href="@{#}" class="review__modi">수정</a><span>|</span><a th:href="@{#}" class="review__del">삭제</a></p></div>`;
		html +=`			<p class="review__reply"><i class="fas fa-level-up-alt fa-rotate-90"></i><span>사장님 : 사장님 댓글</span></p>`;
		html +=`		</div>`;
		html +=`		<div class="review__column"><img class="review__img" src="https://picsum.photos/id/93/180/130" alt="리뷰첨부사진"></div>`;
		html +=`	 </div>`;
	});
	document.querySelector('.review').innerHTML = html;

}
