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

//  리뷰등록
const regiBtn_f = e =>{
	
	console.log('리뷰등록');
	
	const rcontent = document.querySelector('.reviewform .review__textarea');
	const rscore = document.querySelector('.reviewform input[name="rscore"]:checked');

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
							//등록후 별점도 초기화?
					}else{
						alert(res.rtmsg);
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
	let modiForms = document.querySelectorAll('.review__modiForm');
	
	//다른 수정폼 있을시
	if(modiForms.length!=0){
		if(confirm("이미 수정 중인 내용은 삭제됩니다.")){
			modiForms.forEach(ele=>ele.remove());
		}
	}
	//새로운 수정폼, 기존 리뷰 아래 생성
	const modiForm = document.createElement('div');
	modiForm.classList.add('review__modiForm');
	e.target.closest('.review__column').append(modiForm);
	// e.target.closest('.review__column').append('<div class="review__modiForm"></div>');
	//원래 내용은 숨김처리
	e.target.closest('.review__main-text').classList.add('hidden');
	
	const rnum = e.target.dataset.rnum;
	const rid = e.target.dataset.id;

	const URL = `/inquire/?rnum=${rnum}`;
	// &id=${rid}`;

	request.get(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == "00"){
							//성공로직처리
							const data = res.data;
							//리뷰폼띄우기
							reviewModiForm(data);
					}else{
							throw new Error(res.rtmsg);
					}
			})
			.catch(err=>{
				//오류로직 처리
					console.log(err.message);
					alert(err.message);
			})
	
}

//리뷰수정폼출력 //생성된 디브의 inner에 삽입
function reviewModiForm(review) {

	let html ='';
	switch(review.rscore){
		case 1:
			html += `<div class="rscore" ${review.rscore}>`;
			html += `	<input type="radio" name="rscore" id="mpoint1" value="1" title="1점" hidden checked>`;
			html += `	<label for="mpoint1"><i class="fas fa-star reviewModiForm__score modi-one reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint2" value="2" title="2점" hidden>`;
			html += `	<label for="mpoint2"><i class="fas fa-star reviewModiForm__score modi-two"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint3" value="3" title="3점" hidden>`;
			html += `	<label for="mpoint3"><i class="fas fa-star reviewModiForm__score modi-three"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint4" value="4" title="4점" hidden>`;
			html += `	<label for="mpoint4"><i class="fas fa-star reviewModiForm__score modi-four"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint5" value="5" title="5점" hidden>`;
			html += `	<label for="mpoint5"><i class="fas fa-star reviewModiForm__score modi-five"></i></label>`;
			html += `</div>`;
		break;
		case 2:
			html += `<div class="rscore" ${review.rscore}>`;
			html += `	<input type="radio" name="rscore" id="mpoint1" value="1" title="1점" hidden>`;
			html += `	<label for="mpoint1"><i class="fas fa-star reviewModiForm__score modi-one reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint2" value="2" title="2점" hidden checked>`;
			html += `	<label for="mpoint2"><i class="fas fa-star reviewModiForm__score modi-two reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint3" value="3" title="3점" hidden>`;
			html += `	<label for="mpoint3"><i class="fas fa-star reviewModiForm__score modi-three"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint4" value="4" title="4점" hidden>`;
			html += `	<label for="mpoint4"><i class="fas fa-star reviewModiForm__score modi-four"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint5" value="5" title="5점" hidden>`;
			html += `	<label for="mpoint5"><i class="fas fa-star reviewModiForm__score modi-five"></i></label>`;
			html += `</div>`;
		break;
		case 3:
			html += `<div class="rscore" ${review.rscore}>`;
			html += `	<input type="radio" name="rscore" id="mpoint1" value="1" title="1점" hidden>`;
			html += `	<label for="mpoint1"><i class="fas fa-star reviewModiForm__score modi-one reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint2" value="2" title="2점" hidden>`;
			html += `	<label for="mpoint2"><i class="fas fa-star reviewModiForm__score modi-two reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint3" value="3" title="3점" hidden checked>`;
			html += `	<label for="mpoint3"><i class="fas fa-star reviewModiForm__score modi-three reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint4" value="4" title="4점" hidden>`;
			html += `	<label for="mpoint4"><i class="fas fa-star reviewModiForm__score modi-four"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint5" value="5" title="5점" hidden>`;
			html += `	<label for="mpoint5"><i class="fas fa-star reviewModiForm__score modi-five"></i></label>`;
			html += `</div>`;
		break;
		case 4:
			html += `<div class="rscore" ${review.rscore}>`;
			html += `	<input type="radio" name="rscore" id="mpoint1" value="1" title="1점" hidden>`;
			html += `	<label for="mpoint1"><i class="fas fa-star reviewModiForm__score modi-one reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint2" value="2" title="2점" hidden>`;
			html += `	<label for="mpoint2"><i class="fas fa-star reviewModiForm__score modi-two reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint3" value="3" title="3점" hidden>`;
			html += `	<label for="mpoint3"><i class="fas fa-star reviewModiForm__score modi-three reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint4" value="4" title="4점" hidden checked>`;
			html += `	<label for="mpoint4"><i class="fas fa-star reviewModiForm__score modi-four reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint5" value="5" title="5점" hidden>`;
			html += `	<label for="mpoint5"><i class="fas fa-star reviewModiForm__score modi-five"></i></label>`;
			html += `</div>`;
		break;
		case 5:
			html += `<div class="rscore" ${review.rscore}>`;
			html += `	<input type="radio" name="rscore" id="mpoint1" value="1" title="1점" hidden>`;
			html += `	<label for="mpoint1"><i class="fas fa-star reviewModiForm__score modi-one reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint2" value="2" title="2점" hidden>`;
			html += `	<label for="mpoint2"><i class="fas fa-star reviewModiForm__score modi-two reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint3" value="3" title="3점" hidden>`;
			html += `	<label for="mpoint3"><i class="fas fa-star reviewModiForm__score modi-three reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint4" value="4" title="4점" hidden>`;
			html += `	<label for="mpoint4"><i class="fas fa-star reviewModiForm__score modi-four reviewForm__checked"></i></label>`;
			html += `	<input type="radio" name="rscore" id="mpoint5" value="5" title="5점" hidden checked>`;
			html += `	<label for="mpoint5"><i class="fas fa-star reviewModiForm__score modi-five reviewForm__checked"></i></label>`;
			html += `</div>`;
		break;

	} 
	html += `<div class="review__contents">`;
	html += `	<div class="warning_msg">5자 이상으로 작성해 주세요.</div>`;
	html += `	<textarea cols="30" rows="10" class="review__textarea-modi" name="rcontent">${review.rcontent}</textarea>`;
	html += `	<button data-rnum="${review.rnum}" class="review__modi" type="button">수정</button>`;
	html += `	<button class="review__modiCancle" type="button">취소</button>`;
	html += `</div>`;

	document.querySelector('.review__modiForm').innerHTML = html;
	modi_score();
	const modiBtn = document.querySelector('.review__modi');		//수정처리
	const cancleBtn = document.querySelector('.review__modiCancle');		//수정취소
	modiBtn.addEventListener('click', modiBtn_f);
	cancleBtn.addEventListener('click',e=>{ 
		document.querySelector('.review__modiForm').remove()
		document.querySelectorAll('.review__main-text').forEach(ele=>ele.classList.remove('hidden'));
	});
}

//평점수정 함수
	//리뷰 수정별점
	function modi_score(){
		let mone = document.querySelector('.modi-one');
		let mtwo = document.querySelector('.modi-two');
		let mthree = document.querySelector('.modi-three');
		let mfour = document.querySelector('.modi-four');
		let mfive = document.querySelector('.modi-five');
	
	
		const mstar = document.querySelectorAll('.reviewModiForm__score');
	
		let score=0;
		
		function mscore1(){
			mstar.forEach(ele=>ele.classList.remove('reviewForm__checked'));
			mone.classList.add('reviewForm__checked');
			score=1
		}
		function mscore2(){
			mscore1();
			mtwo.classList.add('reviewForm__checked');
			score=2
		}
		function mscore3(){
			mscore2();
			mthree.classList.add('reviewForm__checked');
			score=3
		}
		function mscore4(){
			mscore3();
			mfour.classList.add('reviewForm__checked');
			score=4
		}
		function mscore5(){
			mscore4();
			mfive.classList.add('reviewForm__checked');
			score=5
		}
	
		mone.addEventListener('click',mscore1);
		mtwo.addEventListener('click',mscore2);
		mthree.addEventListener('click',mscore3);
		mfour.addEventListener('click',mscore4);
		mfive.addEventListener('click',mscore5);
	}
	

//리뷰수정처리
const modiBtn_f = e =>{

	console.log('리뷰수정');
	
  const rnum = e.target.dataset.rnum;
	const modiContent = document.querySelector('.review__textarea-modi');
	const modiRscore = document.querySelector('.review__modiForm input:checked');
	
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
			"rscore" : modiRscore.value,
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
	// const rid = e.target.dataset.id;
	
	const URL = `/inquire/?bnum=${bnum}&rnum=${rnum}`;
	
	request.delete(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == "00"){
							//성공로직처리
							const data = res.data;
							//댓글목록갱신
							refreshReview(data);
					}else{
						alert(res.rtmsg);
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
	html += `<div class="review__cnt"><i class="far fa-comment-dots"></i><span>리뷰수 : ${data.length}</span></div>`;
	html += `<section class="review">`;
	data.forEach(review=>{
		html += `<div class="review__row" data-rnum="${review.rnum}">`;
		html += `	<div class="review__column">`;
		// if(review.store_fname != null){ 
		// 	html += `<img class="review__profile" src="/img/upload/member/${review.store_fname}" alt="프로필사진">`;
		// } else{
		// 	html += `<img class="review__profile" src="/img/upload/member/puppy.png" alt="기본프로필사진">`
		// }
		review.store_fname
				 ? html += `<img class="review__profile" src="/img/upload/member/${review.store_fname}" alt="프로필사진">` 
				 : html += `<img class="review__profile" src="/img/upload/member/puppy.png" alt="기본프로필사진">`;
		html += `		<span class="review__nickname">${review.nickname}</span>`;
		html += ` </div>`;
		html += `	<div class="review__column">`;
		html += `		<div>`
		html += `			<span class="review__date">작성일자 : ${review.rvcdate}</span>`;
		if(review.rvudate) {
			html += `<span class="review__isUpdate">수정됨</span>`;
		}
		html += `		</div>`
		html += `		<div class="review__main-text">`
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
		html += `		</div>`;
		html += `	</div>`;
		html += `	<div class="review__column"><img class="review__img" src="https://picsum.photos/id/93/180/130" alt="리뷰첨부사진"></div>`;
		html += `</div>`;
	})
	html += `</section>`;
	document.querySelector('.review__container').innerHTML = html;
			
	//버튼 재호출 + 이벤트리스너 재등록
	modiFrmBtns = document.querySelectorAll('.review__modi-frm'); //수정폼띄우기
	modiFrmBtns.forEach(ele=>ele.addEventListener('click',modiFrmBtns_f));

	delBtns = document.querySelectorAll('.review__del');		//삭제하기
	delBtns.forEach(ele=>ele.addEventListener('click',delBtn_f));
}
//각 버튼 이벤트리스너 등록
regiBtn.addEventListener('click',regiBtn_f);
delBtns.forEach(ele=>ele.addEventListener('click',delBtn_f));
modiFrmBtns.forEach(ele=>ele.addEventListener('click',modiFrmBtns_f));


//회원리뷰에 사장님 댓글


const replyBtns_f = e =>{

	const rnum = e.target.dataset.rnum;
	const bid = $busi.id;

	const URL = `/inquire/rvreply?rnum=${rnum}&bid=${bid}`;

	request.get(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == "00"){
							//성공로직처리
							const data = res.data;
							//리댓폼 출력
							addReply(data);
					}else{
							throw new Error(res.rtmsg);
					}
			})
			.catch(err=>{
				//오류로직 처리
					console.log(err.message);
					alert(err.message);
			})
}

function addReply(review){
	let html='';
	html += `<div class="rvReply">`;
	html += `	<div class="warning_msg">5자 이상으로 작성해 주세요.</div>`;
	html += `	<textarea class="rvReply__content" name="rvReply" id="" cols="30" rows="10">${review.rvReply}</textarea>`;
	html += `	<div class="rvReply__btns">`;
	html += `		<button class="rvReply__btn rvReply__addBtn">확인</button>`;
	html += `		<button class="rvReply__btn rvReply__cancle">취소</button>`;
	html += `	</div>`;
	html += `</div>`;

	const replyForm = document.createElement('div');
	replyForm.classList.add('review__reply-form');
	replyForm.innerHTML=html;
	document.querySelector('.review__reply').append(replyForm);

	const addBtn = document.querySelector('.rvReply__addBtn');
	const cancleBtn = document.querySelector('.rvReply__cancle');

	// cancleBtn.addEventListener('click',)
}

//리댓달기 버튼 이벤트
let replyBtns = document.querySelectorAll('.review__replyBtn');
replyBtns?.forEach(ele=>ele.addEventListener('click',replyBtns_f));


