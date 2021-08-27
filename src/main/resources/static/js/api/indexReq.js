/**
 * 
 */
 'use strict'
 

 
 
const $bestBtns = document.querySelectorAll('.board-best__btn');
Array.from($bestBtns).forEach(ele=>{
	ele.addEventListener('click',e=>{
		
	console.log('allBtn_f');
	
	const URL = `/main/${e.target.dataset.category}`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			let html ='';
			data.forEach(post=>{
			html += `<section class="board-best">`; 
html += `    <div class="board-best__row" th:each="post : ${post}">`;
html += `      <div class="board-best__content">`;
html += `        <div> `;
html += `          <h1 class="board-best__title"><a href="" th:href="@{|/board/post/${post.bnum}|}" th:text="${post.btitle}">게시글 제목</a></h1>`;
html += `          <p class="board-best__text" th:text="${post.bcontent}">게시글 내용</p>`;
html += `        </div>`;
html += `        <img class="board-best__img" src="https://picsum.photos/id/501/200/200" alt="">`;
html += `      </div>`;
html += `      <hr> `;
html += `      <div class="good">`;
html += `        <i class="far fa-heart"></i>`;
html += `        <span th:text="|좋아요 ${post.bgood}|">좋아요 수</span>`;
html += `      </div>`;
html += `    </div>`;
html += `  </section>`;
			});
document.querySelector('.boardForm__replyListWrap').innerHTML = html;
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});

	});
});