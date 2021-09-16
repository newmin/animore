/**
 * 
 */
 
    const $addPostBtns = document.querySelectorAll('.boardForm__addBtn');
    for(let i=0; i<$addPostBtns.length; i++){
        $addPostBtns[i].addEventListener('click',e=>{
        const cate = e.target.dataset.cate
        if($id){location.href=`/board?cate=${cate}`}
        else{location.href=`/login?redirectUrl=/board?cate=${cate}`};

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
  const href = location.href.trim().split("/")[4];
  console.log(href);
   if (href.slice(0,1) == "Q"){
    ele_Q.classList.add('board__selectCategory');
  }
  else if (href.slice(0,1) == "M"){
    ele_M.classList.add('board__selectCategory');
  }
  else if (href.slice(0,1) == "F"){
    ele_F.classList.add('board__selectCategory');
  }
   else if (href.slice(0,1) == "P"){
    ele_P.classList.add('board__selectCategory');
  }

  
}


// let page = '';
// page +=`<div th:if="${pc.prev}"> `;
// page +=`    <li class="page-item"> `;
// page +=`            <a class="page-link"  href="{/board/{cate}?reqPage=1(cate=${cate})}"><i class="fas fa-angle-double-left"></i></a> `;
// page +=`        </li> `;
// page +=`    <li class="page-item"> `;
// page +=`            <a class="page-link" href="{/board/{cate}(cate=${cate},reqPage=${pc.startPage-1})}"><i class="fas fa-angle-left"></i></a> `;
// page +=`        </li> `;
// page +=`    </div> `;
// page +=`<div th:each="pnum : ${#numbers.sequence(pc.startPage,pc.endPage)}"> `;
// page +=`    <div % if="${pnum != 0}" %> `;
// page +=`            <li class="page-item active" aria-current="page" `;
// page +=`                    th:if="${pc.rc.reqPage == pnum}"> `;
// page +=`        <span class="page-link">${pnum}</span> `;
// page +=`    </li>			 `;				    
// page +=`    <li class="page-item"  `;
// page +=`            th:if="${pc.rc.reqPage != pnum}"> `;
// page +=`        <a class="page-link" href="{/board/{cate}(cate=${cate},reqPage=${pnum})}" >${pnum}</a> `;
// page +=`    </li> `;
// page +=`    </div % endif %> `;
// page +=`</div> `;
// page +=`<div % if="${pc.next}"%> `;
// page +=`    <li class="page-item"> `;
// page +=`            <a class="page-link" href="{/board/{cate}(cate=${cate},reqPage=${pc.endPage+1})}"><i class="fas fa-angle-right"></i></a> `;
// page +=`        </li> `;
// page +=`    <li class="page-item"> `;
// page +=`            <a class="page-link" href="{/board/{cate}(cate=${cate},reqPage=${pc.finalEndPage})}"><i class="fas fa-angle-double-right"></i></a> `;
// page +=`        </li>	 `;	
// page +=`</div %endif%> `;

// const $url = document.location.href;
// /*제목으로게시글찾기*/
// function searchByBtitle(){

// const URL  =`/board/search/title/${$url.slice(-1,$url.length)}?btitle=${$searchText.value}`;
// console.log("url:",URL);

// request.get(URL)
// 		.then(res=>res.json())
// 		.then(res=>{
// 		if(res.rtcd=='00'){
// 		const data = res.data;
// 		const pc = res.data2;
//     if($url.slice(-1,$url.length)=='P'){
//       let html ='';
//       html += `  <div class="b_galary_post"> `;
//       data.forEach(boardForm=>{
//         html += `<div class="b_galary_img"><a href="/board/post/${boardForm.bnum}"> `;
//         html += `  <img src="https://picsum.photos/id/1/200/200" alt=""> <p class="b_galary_title">${boardForm.btitle}</p></a></div> `;
//       })
//       html += `</div>`;
//       document.querySelector('.board__table').innerHTML = html;
//       $paging.innerHTML = page;
//     }else{
// 		let html ='';
// 		html += `    <tr> `;
//           html += `      <th class="board__cell board__num">번호</th> `;
//           html += `      <th class="board__cell board__titles">제목</th> `;
//           html += `      <th class="board__cell board__writer">작성자</th> `;
//           html += `      <th class="board__cell board__hits">조회수</th> `;
//           html += `      <th class="board__cell board__hearthits">좋아요</th> `;
//           html += `    </tr> `;
// 		data.forEach(boardForm=>{
//     html +=`<tr>`;
//       html +=` <td class="board__cell board__num" >${boardForm.bnum}</td> `;
//       html +=` <td class="board__cell board__title" ><a href="/board/post/${boardForm.bnum}" >${boardForm.btitle}</a></td> `;
//       html +=` <td class="board__cell board__writer" >${boardForm.nickname}</td> `;
//       html +=` <td class="board__cell board__hits" >${boardForm.bhit}</td> `;
//       html +=` <td class="board__cell board__hearthits" >${boardForm.bgood}</td> `;
//     html +=`</tr> `;
// 		});
// 		document.querySelector('.board__table').innerHTML = html;
// 		 $paging.innerHTML = page;}
//   }else{
//   		throw new Error(res.rtmsg);
//   	}
//   })
//   .catch(err=>{
//     //오류로직 처리
// 		console.log (err.message);
//     alert("검색 결과가 없습니다.");
// 	});
  
  	

// };
// /*닉네임으로게시글찾기*/
// function searchByNickname(){

// const URL  =`/board/search/nickname/${$url.slice(-1,$url.length)}?nickname=${$searchText.value}`;
// console.log("url:",URL);

// request.get(URL)
// 		.then(res=>res.json())
// 		.then(res=>{
// 		if(res.rtcd==0){
// 		const data = res.data;
//     if($url.slice(-1,$url.length)=='P'){
//       let html ='';
//       html += `  <div class="b_galary_post"> `;
//       data.forEach(boardForm=>{
//         html += `<div class="b_galary_img"><a href="/board/post/${boardForm.bnum}"> `;
//         html += `  <img src="https://picsum.photos/id/1/200/200" alt=""> <p class="b_galary_title">${boardForm.btitle}</p></a></div> `;
//       })
//       html += `</div>`;
//       document.querySelector('.board__table').innerHTML = html;
//     }else{
// 		let html ='';
// 		html += `    <tr> `;
//           html += `      <th class="board__cell board__num">번호</th> `;
//           html += `      <th class="board__cell board__title">제목</th> `;
//           html += `      <th class="board__cell board__writer">작성자</th> `;
//           html += `      <th class="board__cell board__hits">조회수</th> `;
//           html += `      <th class="board__cell board__hearthits">좋아요</th> `;
//           html += `    </tr> `;
// 		data.forEach(boardForm=>{
//     html +=`<tr>`;
//       html +=` <td class="board__cell board__num" >${boardForm.bnum}</td> `;
//       html +=` <td class="board__cell board__title" ><a href="/board/post/${boardForm.bnum}" >${boardForm.btitle}</a></td> `;
//       html +=` <td class="board__cell board__writer" >${boardForm.nickname}</td> `;
//       html +=` <td class="board__cell board__hits" >${boardForm.bhit}</td> `;
//       html +=` <td class="board__cell board__hearthits" >${boardForm.bgood}</td> `;
//     html +=`</tr> `;
// 		});
// 		document.querySelector('.board__table').innerHTML = html;}
//   }else{
//   		throw new Error(res.rtmsg);
//   	}
//   	})
//   	.catch(err=>{
// 		//오류로직 처리
// 		console.log (err.message);
//     alert("검색 결과가 없습니다.");
// 	});
  
  	

// };
/*본문으로게시글찾기*/
// function searchByBcontent(){

// const URL  =`/board/search/content/${$url.slice(-1,$url.length)}?bcontent=${$searchText.value}`;
// console.log("url:",URL);

// request.get(URL)
// 		.then(res=>res.json())
// 		.then(res=>{
// 		if(res.rtcd==0){
// 		const data = res.data;
//     if($url.slice(-1,$url.length)=='P'){
//       let html ='';
//       html += `  <div class="b_galary_post"> `;
//       data.forEach(boardForm=>{
//         html += `<div class="b_galary_img"><a href="/board/post/${boardForm.bnum}"> `;
//         html += `  <img src="https://picsum.photos/id/1/200/200" alt=""> <p class="b_galary_title">${boardForm.btitle}</p></a></div> `;
//       })
//       html += `</div>`;
//       document.querySelector('.board__table').innerHTML = html;
//     }else{
// 		let html ='';
// 		html += `    <tr> `;
//           html += `      <th class="board__cell board__num">번호</th> `;
//           html += `      <th class="board__cell board__title">제목</th> `;
//           html += `      <th class="board__cell board__writer">작성자</th> `;
//           html += `      <th class="board__cell board__hits">조회수</th> `;
//           html += `      <th class="board__cell board__hearthits">좋아요</th> `;
//           html += `    </tr> `;
// 		data.forEach(boardForm=>{
//     html +=`<tr>`;
//       html +=` <td class="board__cell board__num" >${boardForm.bnum}</td> `;
//       html +=` <td class="board__cell board__title" ><a href="/board/post/${boardForm.bnum}" >${boardForm.btitle}</a></td> `;
//       html +=` <td class="board__cell board__writer" >${boardForm.nickname}</td> `;
//       html +=` <td class="board__cell board__hits" >${boardForm.bhit}</td> `;
//       html +=` <td class="board__cell board__hearthits" >${boardForm.bgood}</td> `;
//     html +=`</tr> `;
// 		});
// 		document.querySelector('.board__table').innerHTML = html;}
//   }else{
//   		throw new Error(res.rtmsg);
//   	}
//   	})
//   	.catch(err=>{
// 		//오류로직 처리
// 		console.log (err.message);
//     alert("검색 결과가 없습니다.");
// 	});
  
  	

// };

//$searchBox.addEventListener('submit',e=>{
//  e.preventDefault();
//console.log(`url은? ${$url.slice(-1,$url.length)}`);
//if($search_select.value == "btitle") {searchByBtitle();}
//if($search_select.value == "nickname") {searchByNickname()};
//if($search_select.value == "bcontent") {searchByBcontent()};
//});

const $searchBox = document.querySelector('.board__searchForm');
const $paging = document.querySelector('.board__paging');
const $searchBtn 	= document.getElementById('searchBtn');
const $searchType = document.getElementById('searchType');
const $keyword 		= document.getElementById('keyword');


	
//검색 버튼 클릭시
$searchBtn.addEventListener('click',e=>{
	
	//검색어입력유무
	if($keyword.value.trim().length == 0){
		alert('검색어를 입력하세요');
		$keyword.focus();
		$keyword.select();
		return false;
	}
	
		const cate = e.target.dataset.cate;
		location.href = `/board/${cate}/1/${$searchType.value}/${$keyword.value}`;
});

//검색입력창 엔터키 눌렀을때
$keyword.addEventListener('keydown',e=>{
	console.log(e.key);
	if(e.key == 'Enter'){ //엔터키
		e.preventDefault();
		//검색어입력유무
		if(e.target.value.trim().length == 0){
			alert('검색어를 입력하세요');
			e.target.focus();
			e.target.select();
			return false;
		}
	
		const cate = e.target.dataset.cate;
		location.href = `/board/${cate}/1/${$searchType.value}/${$keyword.value}`;

	}
});


//$searchBox.addEventListener('submit',e=>{
//  console.log('검색버튼');
//const cate = e.target.dataset.cate;
//if($search_select.value == "btitle") {searchByBtitle()};
//if($search_select.value == "nickname") {searchByNickname()};
//if($search_select.value == "bcontent") {searchByBcontent()};
//});

	
    