/**
 * 
 */
 
    const $addPostBtns = document.querySelectorAll('.boardForm__addBtn');
    for(let i=0; i<$addPostBtns.length; i++){
        $addPostBtns[i].addEventListener('click',e=>{
            location.href="/board/";
            console.log('클릭! ');
        });
    }
    
    let ele_Q = document.getElementById('category_Q');
  	let ele_M = document.getElementById('category_M');
  	let ele_F = document.getElementById('category_F');
  	let ele_P = document.getElementById('category_P');
   	const $categorys = document.querySelectorAll('.board__category');
   	
   document.addEventListener("DOMContentLoaded", function(){
      SetMenu();
});
   function InitMenu(){
  ele_Q.classList.remove('board__selectCategory');
  ele_M.classList.remove('board__selectCategory');
  ele_F.classList.remove('board__selectCategory');
  ele_P.classList.remove('board__selectCategory');
}
  function SetMenu(){
  InitMenu(); //초기화
   if (location.href == "http://localhost:8081/board/Q"){
    ele_Q.classList.add('board__selectCategory');
  }
  else if (location.href == "http://localhost:8081/board/M"){
    ele_M.classList.add('board__selectCategory');
  }
  else if (location.href == "http://localhost:8081/board/F"){
    ele_F.classList.add('board__selectCategory');
  }
   else if (location.href == "http://localhost:8081/board/P"){
    ele_P.classList.add('board__selectCategory');
  }

  
}

const $search_select = document.querySelector('.board__search_select');
const $searchText = document.querySelector('.board__searchInput');
const $searchBtn = document.querySelector('.searchBtn');
const $url = document.location.href;
/*제목으로게시글찾기*/
function searchByBtitle(){

const URL  =`/board/search/${$url.slice(-1,$url.length)}?btitle=${$searchText.value}`;
console.log("url:",URL);

request.get(URL)
		.then(res=>res.json())
		.then(res=>{
		if(res.rtcd==0){
		const data = res.data;
		let html ='';
		html += `    <tr> `;
          html += `      <th class="board__cell board__num">카테고리</th> `;
          html += `      <th class="board__cell board__title">제목</th> `;
          html += `      <th class="board__cell board__writer">작성자</th> `;
          html += `      <th class="board__cell board__hits">조회수</th> `;
          html += `      <th class="board__cell board__hearthits">좋아요</th> `;
          html += `    </tr> `;
		data.forEach(boardForm=>{
    html +=`<tr>`;
      html +=` <td class="board__cell board__num" >${boardForm.bcategory}</td> `;
      html +=` <td class="board__cell board__title" ><a href="/board/post/${boardForm.bnum}" >${boardForm.btitle}</a></td> `;
      html +=` <td class="board__cell board__writer" >${boardForm.nickname}</td> `;
      html +=` <td class="board__cell board__hits" >${boardForm.bhit}</td> `;
      html +=` <td class="board__cell board__hearthits" >${boardForm.bgood}</td> `;
    html +=`</tr> `;
		});
		document.querySelector('.board__table').innerHTML = html;
  }else{
  		throw new Error(res.rtmsg);
  	}
  	})
  	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});
  
  	

};


$searchBtn.addEventListener('click',e=>{
console.log(`url은? ${$url.slice(-1,$url.length)}`);
if($search_select.value == "btitle") {searchByBtitle();}
if($search_select.value == "nickname") {searchByNickname()};
if($search_select.value == "bcontent") {searchByBcontent()};
});

	
    