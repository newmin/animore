'use strict';

//체크박스 전체선택
bcategory_all.addEventListener('click',()=>{
  const checkboxes = document.querySelectorAll('input[type=checkbox]');
  checkboxes.forEach(checkbox=>{
    checkbox.checked = bcategory_all.checked
  });
});

const $mypostBtn = document.querySelector('.mypage__mypostBtn');
let $contents = document.querySelector('.mypage_content');
let $titleNm = document.querySelector('.mypage_content_title');
$mypostBtn.addEventListener('click',e=>{
//alert('내글보기버튼 클릭');
const URL =`/mypage/mypost`;
	console.log("url:",URL);
	
request.get(URL)
      .then(res=>res.json())
      .then(res=>{
        if(res.rtcd == '00'){
          const data = res.data;
          let html ='';
          html += `<table class="mypage__post"> `;
          html += `    <tr> `;
          html += `      <th class="mypost__cell mypost__bcategory">카테고리</th> `;
          html += `      <th class="mypost__cell mypost__btitle">제목</th> `;
          html += `      <th class="mypost__cell mypost__udate">작성일</th> `;
          html += `      <th class="mypost__cell mypost__bhit">조회수</th> `;
          html += `    </tr> `;
          data.forEach(post =>{
              html += `<tr class="mypost__container"> `;
              html += `    <td class="mypost__cell mypost__bcategory ">${post.bcategory}</td> `;
              html += `    <td class="mypost__cell mypost__btitle" ><a th:href="@{|/board/post/${post.bnum}|}" >${post.btitle}</a></td> `;
              html += `    <td class="mypost__cell mypost__udate" >${post.bcdate}</td> `;
              html += `    <td class="mypost__cell mypost__bhit" >${post.bhit}</td> `;
              html += `</tr> `;
          });
          html += `</table> `;
          $contents.innerHTML = html;
          $titleNm.textContent = '내가 쓴 글';
        }else{
          throw new Error(res.rtmsg);
        }
      })
      .catch(err=>{
		//오류로직 처리
		console.log (err.message);
		});
});