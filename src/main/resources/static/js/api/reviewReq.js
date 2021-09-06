/**
 * 
 */
 'use strict'
 
//  호출하는 값
//const $id = html에서 호출	//아이디
const bnum = document.querySelector('.selected-busi').dataset.bnum;		//업체번호
//  버튼
const regiBtn = document.querySelector('.review__regist');  //등록하기
let modiFrmBtns = document.querySelectorAll('.review__modi-frm'); //수정폼띄우기
let delBtns = document.querySelectorAll('.review__del');		//삭제하기
//리뷰 작성폼
const rcontent = document.querySelector('.review__textarea');
const rscore = document.querySelector('input[name="rscore"]:checked');
const modiContent = document.querySelector('.review__textarea-modi');


//  리뷰등록
const regiBtn_f = e =>{
	
	console.log('리뷰등록');
	
	//리뷰입력체크
	if(!rcontent.value) {
			alert("리뷰 내용을 입력하세요");
			return;
	}	
	
	const URL = `/inquire/`;
	const data = {
			"bnum" : bnum,
			"rcontent": rcontent.value,
			"rscore" : rscore.value,
			"id" : $id
													 };
													 	
	request.post(URL,data)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == '00'){
							//성공로직처리
							const data = res.data;
							//리뷰목록갱신
							refreshReview(data);
							//리뷰입력창 초기화
							rcontent.value=null;
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

//수정폼 버튼 클릭시, 수정폼+수정버튼 출력
const modiFrmBtns_f = e=> {
	const rnum = e.target.dataset.rnum;
	const rid = e.target.dataset.id;

	const URL = `/inquire/?rnum=${rnum}`;

	request.get(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == '00'){
							//성공로직처리
							const data = res.data;
							alert('되니?')
							//리뷰목록갱신
							reviewModiForm(data);
							alert('됐니?')
					}else{
							throw new Error(res.rtmsg);
							alert('else');
					}
			})
			.catch(err=>{
					//오류로직 처리
					console.log(err.message);
					alert(err.message);
			});


}

//리뷰수정폼출력 //생성된 디브의 inner에 삽입
function reviewModiForm(review){

			//length!=0으로 안되면 !=null로
			const modiForms = document.querySelectorAll('.review__modiForm');
			//다른 수정폼 있을시
			if(modiForms.length!=0){
				if(confirm("이미 수정 중인 내용은 삭제됩니다.")){
					modiForms.forEach(ele=>ele.remove());
				}
			}
			//새로운 수정폼, 기존 리뷰 아래 생성
			const review__modiForm = document.createElement('div');
			review__modiForm.classList.add('review__modiForm');
			e.closest('.review__main-text').after(review__modiForm);
			//원래 내용은 숨김처리
			document.querySelector('.review__main-text').classList('hidden');


	let html ='';
	html += `		<div>`
	html += `			<span class="review__nickname">${review.nickname}</span>`;
	html += `			<span class="review__date">작성일자 : ${review.rvcdate}</span>`;
	html += `		<div>`

	html += `<div class="rscore">`;
	html += `	<input type="radio" name="rscore" id="point1" value="1" title="1점" hidden>`;
	html += `	<label for="point1"><i class="fas fa-star reviewForm__score one reviewForm__checked"></i></label>`;
	html += `	<input type="radio" name="rscore" id="point2" value="2" title="2점" hidden>`;
	html += `	<label for="point2"><i class="fas fa-star reviewForm__score two reviewForm__checked"></i></label>`;
	html += `	<input type="radio" name="rscore" id="point3" value="3" title="3점" hidden>`;
	html += `	<label for="point3"><i class="fas fa-star reviewForm__score three reviewForm__checked"></i></label>`;
	html += `	<input type="radio" name="rscore" id="point4" value="4" title="4점" hidden>`;
	html += `	<label for="point4"><i class="fas fa-star reviewForm__score four reviewForm__checked"></i></label>`;
	html += `	<input type="radio" name="rscore" id="point5" value="5" title="5점" hidden>`;
	html += `	<label for="point5"><i class="fas fa-star reviewForm__score five reviewForm__checked"></i></label>`;
	html += `</div>`;
	html += `<div class="review__contents">`;
	html += `	<div class="warning_msg">5자 이상으로 작성해 주세요.</div>`;
	html += `	<textarea rows="10" class="review__textarea" name="rcontent">${review.rcontent}</textarea>`;
	html += `	<button data-rnum="${review.rnum}" class="review__modi" type="button">수정</button>`;
	html += `	<button class="review__modiCancle" type="button">취소</button>`;
	html += `</div>`;

	document.querySelector('.review__column:nth-child(2)').innerHTML = html;

	const modiBtn = document.querySelector('.review__modi');		//수정처리
	const cancleBtn = document.querySelector('.review__modiCancle');		//수정취소
	modiBtn.addEventListener('click',modiBtn_f);
	cancleBtn.addEventListener('click',document.querySelector('.review__modiForm').remove());
}

//리뷰수정처리
const modiBtn_f = e =>{

	console.log('리뷰수정');
	
  const rnum = e.target.dataset.rnum;
	
	//리뷰입력체크
	if(!modiContent.value) {
			alert("리뷰 내용을 입력하세요");
			return;
	}	
	
	const URL = `/inquire/`;
	const data = {
			"bnum": bnum,
			"rnum": rnum,
			"rcontent": modiContent.value,
			"rscore" : rscore.value,
			"id" : $id
													 };
	
	request.patch(URL,data)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == '00'){
							//성공로직처리
							const data = res.data;
							//댓글목록갱신
							refreshReview(data);
					}else{
							throw new Error(res.rtmsg);
					}
			})
			.catch(err=>{
					//오류로직 처리
					console.log(err.message);
					alert(err.message);
			});
};//수정처리

//리뷰삭제
const delBtn_f = e => {
	if(!confirm('한번 삭제한 리뷰는 되돌릴 수 없어요. 정말 리뷰를 지우시겠어요?')){
		return;
	}
	
	const rnum = e.target.dataset.rnum;
	const rid = e.target.dataset.id;
	
	const URL = `/inquire/?bnum=${bnum}&rnum=${rnum}&id=${rid}`;
	
	request.delete(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == '00'){
							//성공로직처리
							const data = res.data;
							//댓글목록갱신
							refreshReview(data);
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
function refreshReview(data){
	let html ='';
	html += `<div><p>리뷰수 : ${data.length}</p></div>`;
	html += `<section class="review">`;
	data.forEach(review=>{
		html += `<div class="review__row" data-rnum="${review.rnum}">`;
		html += `	<div class="review__column"><img class="review__profile" src="https://picsum.photos/id/10/50/50" alt="프로필사진"></div>`;
		html += `	<div class="review__column">`;
		html += `		<div>`
		html += `			<span class="review__nickname">${review.nickname}</span>`;
		html += `			<span class="review__date">작성일자 : ${review.rvcdate}</span>`;
		html += `		</div>`
		switch(review.rscore){
			case 1:
				html += `			<div class="review__star-score">`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `			</div>`;
			break;
			case 2:
				html += `			<div class="review__star-score">`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `			</div>`;
			break;
			case 3:
				html += `			<div class="review__star-score">`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `			</div>`;
			break;
			case 4:
				html += `			<div class="review__star-score">`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="far fa-star"></i>`;
				html += `			</div>`;
			break;
			case 5:
				html += `			<div class="review__star-score">`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `				<i class="fas fa-star"></i>`;
				html += `			</div>`;
			break;
		}
		html += `		<p class="review__content">${review.rcontent}</p>`;
		if($id == review.id){
			html += `		<div>`;
			html += `			<p class="review__userBtns">`;
			html += `				<span data-rnum="${review.rnum}" data-id="${review.id}" class="review__userBtn review__modi-frm">수정</span>`;
			html += `				<span>|</span>`;
			html += `				<span data-rnum="${review.rnum}" data-id="${review.id}" class="review__userBtn review__del">삭제</span>`;
			html += `			</p>`;
			html += `		</div>`;
		}
		html += `		<p class="review__reply"><i class="fas fa-level-up-alt fa-rotate-90"></i><span>사장님 : 사장님 댓글</span></p>`;
		html += `	</div>`;
		html += `	<div class="review__column"><img class="review__img" src="https://picsum.photos/id/93/180/130" alt="리뷰첨부사진"></div>`;
		html += `</div>`;
	})
	html += `</section>`;
	document.querySelector('.review__container').innerHTML = html;
			
	//버튼 재호출
	modiFrmBtns = document.querySelectorAll('.review__modi-frm'); //수정폼띄우기
	delBtns = document.querySelectorAll('.review__del');		//삭제하기
	//이벤트리스너 재등록
	modiFrmBtns.forEach(ele=>ele.addEventListener('click',modiFrmBtns_f));
	delBtns.forEach(ele=>ele.addEventListener('click',delBtn_f));
}
//각 버튼 이벤트리스너 등록
regiBtn.addEventListener('click',regiBtn_f);
delBtns.forEach(ele=>ele.addEventListener('click',delBtn_f));
modiFrmBtns.forEach(ele=>ele.addEventListener('click',modiFrmBtns_f));