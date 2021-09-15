/**
 * 
 */
 'use strict'
 

 
 
const $bestBtns = document.querySelectorAll('.board-best__btn');
Array.from($bestBtns).forEach(ele=>{

	ele.addEventListener('click',e=>{
		
	console.log('allBtn_f');
	
	Array.from($bestBtns).forEach(btn=>{btn.classList.remove('selectedBoard');})
	e.target.classList.add('selectedBoard');
	
	
	const URL = `/main/${e.target.dataset.category}`;
	console.log("url:",URL);
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			let html ='';
			data.forEach(post=>{
html += `    <div class="board-best__row" >`;
html += `      <div class="board-best__content">`;
html += `        <div> `;
html += `          <h1 class="board-best__title"><a href="/board/post/${post.bnum}" >${post.btitle}</a></h1>`;
html += `          <p class="board-best__text" >${post.bcontent}</p>`;
html += `        </div>`;
if(post.files.length != 0){
html += `    <a href="{/board/post/${post.bnum}}"> `;
html += `     <img class="b_galary_img board-best__img"  src="/img/upload/board/${post.files[0].store_fname}"></img> `;
html += `    </a>`; 
} else{
html += `<a href="{/board/post/${post.bnum}}"> `;
html += `    <i class="fas fa-paw"></i>`;
html += `   </a>`;
}
html += `      </div>`;
html += `      <hr> `;
html += `  <div class="good">`;
html += `      <div class="good">`;
html += `        <i class="fas fa-heart"></i>`;
html += `        <span>좋아요 ${post.bgood}</span>`;
html += `      </div>`;
html += `           <div class="board-best__cdate">${post.bcdate}</div> `;
html += `    </div>`;
html += `   </div>`;
html += `  </section>`;
			});
document.querySelector('.board-best').innerHTML = html;
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