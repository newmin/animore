/**
 * 
 */
 //내가 쓴 리뷰
const myReview =  document.querySelector('.mypage__myReviewBtn')

function review(){
  const URL = `/mypage/review`;
													 	
	request.get(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == "00"){
							//성공로직처리
							const data = res.data;
							//리뷰목록갱신
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
}

function refreshReview(data){
  let html= '';
  html += `<h2>내가 쓴 리뷰</h2>`;
  html += `<hr>`;
  html += `<table class="review_table">`;
  html += `<tr>`;
  html += `  <th class="review__cell">번호</span>`;
  html += `  <th class="review__cell">업체명</span>`;
  html += `  <th class="review__cell">내평점</span>`;
  html += `  <th class="review__cell">리뷰내용</span>`;
  html += `  <th class="review__cell">작성일</span>`;
  html += `</tr>`;
  data.forEach(review => {
    html += `<tr>`;
    html += `    <td class="review__cell">번호</span>`;
    html += `    <td class="review__cell">${review.bname}</span>`;
    html += `    <td class="review__cell">${review.rscore}</span>`;
    html += `    <td class="review__cell">${review.rcontent}</span>`;
    html += `    <td class="review__cell">${review.rvcdate}</span>`;
    html += `</tr>`;
  });
  
  document.querySelector('.mypage_content_container').innerHTML = html;

}

myReview.addEventListener('click',review);



//내가 쓴 글
const $mypostBtn = document.querySelector('.mypage__mypostBtn');
let $contents = document.querySelector('.mypage_content_container');



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
          html += `<h2 class='mypage_content_title'>내가 쓴 글</h2>`;
          html += `<hr>`;
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
              html += `    <td class="mypost__cell mypost__btitle" ><a href="/board/post/${post.bnum}" >${post.btitle}</a></td> `;
              html += `    <td class="mypost__cell mypost__udate" >${post.bcdate}</td> `;
              html += `    <td class="mypost__cell mypost__bhit" >${post.bhit}</td> `;
              html += `</tr> `;
          });
          html += `</table> `;
          $contents.innerHTML = html;
          const $bcategorys = document.querySelectorAll('.mypost__bcategory');
for(let i =0; i<$bcategorys.length; i++){
  if($bcategorys[i].textContent == 'Q') $bcategorys[i].textContent = 'Q&A';
  if($bcategorys[i].textContent == 'P') $bcategorys[i].textContent = '내새끼보세요';
  if($bcategorys[i].textContent == 'F') $bcategorys[i].textContent = '자유게시판';
  if($bcategorys[i].textContent == 'M') $bcategorys[i].textContent = '벼룩시장';
};
        }else{
          throw new Error(res.rtmsg);
        }
      })
      .catch(err=>{
		//오류로직 처리
		console.log (err.message);
		});
});

//안될시 타게팅만 새로 해주면 됨
//내가쓴댓글
const $mypageReplyMenu = document.querySelector('a[href="/mypage/mypageReply"]');
	$mypageReplyMenu.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mypageReply`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			document.querySelector('.mypage_content_container').innerHTML = data;
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});

});




//좋아요 조회
const $goodBoardList = document.querySelector('a[href="/mypage/mypageGood"]');
	$goodBoardList.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mypageGood`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			document.querySelector('.mypage_content_container').innerHTML = data;
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});

});

//개인정보수정 화면
	const $mypageModify = document.querySelector('a[href="/mypage/mypageModify"]');
	$mypageModify.addEventListener('click',e=>{
		e.preventDefault();
		
		const URL = `/mypage/mypageModify`;
		
		request.get(URL)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
			
				//성공로직처리
				console.log(res);
				const data = res.data;
				document.querySelector('.mypage_content_container').innerHTML = data;
				
				const $modifyBtn = document.querySelector('#modifyBtn');
				$modifyBtn.addEventListener("click", modifyBtn); 
				
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			console.log (err.message);
	});
});

//개인정보 수정처리

const modifyBtn = e =>{
	e.preventDefault();
	
	const $id = id.value;
	const $pw = pw.value;
	// const $name = name.value;
	const $name = document.querySelector('input[name="name"]').value;
	const $tel = tel.value;
	const $nickname = nickname.value;
	const $address = address.value;
	const $birth = birth.value;
	const $email = email.value;
	
	
	
	const URL = `/mypage/mypageModify`;
	const data = {
								 "id":$id,
								 "pw":$pw,
								 "name":$name,
								 "tel":$tel,
								 "email":$email,
								 "nickname":$nickname,
								 "address": $address,
								 "birth" : $birth
							 };
	
	request.patch(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				const data = res.data;
				
				refreshModi(data);				//개인정보수정 리프레쉬
				
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			alert(err.message);
		});
		
		const $modifyBtn = document.querySelector('#modifyBtn');
		$modifyBtn.addEventListener("click", modifyBtn); 
};



//회원탈퇴
const $mypageDelMenu = document.querySelector('a[href="/mypage/mypageDel"]');
	$mypageDelMenu.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mypageDel`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			document.querySelector('.mypage_content_container').innerHTML = data;
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});
});

//비밀번호 변경양식
	const $myPwModi = document.querySelector('a[href="/mypage/mypagePwModify"]');

	$myPwModi.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mypagePwModify`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			document.querySelector('.mypage_content_container').innerHTML = data;
			
				const $changPw = document.querySelector('#changPw');
				$changPw.addEventListener("click", changPw); 
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});

});

//비밀번호 변경처리

const changPw = e =>{
	e.preventDefault();
	
	const $id = id.value;
	const $pw = pw.value;
	const $pwChk =pwChk.value;
	
	
	
	const URL = `/mypage/mypagePwModify`;
	const data = {
								 "id":$id,
								 "pw":$pw,
								 "pwChk":$pwChk
							 };
	
	request.patch(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				const data = res.data;
					refreshPwchange(data);	
				
				
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			alert(err.message);
		});
		
		const $changPw = document.querySelector('#changPw');
		$changPw.addEventListener("click", changPw); 
};


//내업체목록
const $mybusilist = document.querySelector('a[href="/mypage/mybusilist"]');
	$mybusilist.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mybusilist`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			document.querySelector('.mypage_content_container').innerHTML = data;
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});

});

function refreshPwchange(memberDTO){
let html ='';

html+="<h2 class=\"mypage_content_title\">개인정보수정</h2>";

html+="<form class=\"main\" action=\"/mypage/mypagePwModify\"/ method=\"post\" \"><input type=\"hidden\" name = \"_method\" value=\"patch\">";

html+="<hr>";

html+="<hr>";

html+="<ul>";
html+="<li><label for=\"id\">아이디</label></li>";
html+="<li><input type=\"text\" id ='id' name ='id' value="+memberDTO.getId()+" readonly='readonly'/></li>";
html+="<li><label for=\"pw\">현재 비밀번호</label></li>";
html+="<li><input type=\"password\" name='pw' id = 'pw' \"/></li>";
html+="<li><label for=\"pw2\">새로운 비밀번호</label></li>";
html+="<li><input type=\"password\" name='pwChk' id = 'pwChk' \"/></li>";
html+="<li><label for=\"pw3\">새로운 비밀번호 확인</label></li>";
html+="<li><input type=\"password\" name='pwChk' id = 'pwChk' \"/></li>";
html+="<li><input class=\"pwModi_btn\" type=\"button\" value='비밀번호수정' id=\"changPw\"></li>";
html+="</form>";
html+="</ul>";
html+="</div>";
};

function refreshModi(memberDTO){
	let html ='';
		html+="<div class=\"mypage_content_container\">";
		
		html+="<h2 class=\"mypage_content_title\">개인정보수정</h2>";
		
		html+="<hr>";
		
		html+="<form class=\"main\" action='/mypage/mypageModify'/ method=\"post\" \"><input type=\"hidden\" name = \"_method\" value=\"patch\">";

		
		
		html+="<li><label for=\"id\">아이디</label></li>";
		html+="<li><input type=\"text\" id ='id' name ='id' value="+memberDTO.id+" readonly=\"readonly\"/></li>";
		
		html+="<li><label for=\"pw\">비밀번호</label></li>";
		html+="<li><input type=\"password\" name='pw' id = 'pw' \"/></li>";
		
		html+="<li>";
		//html+="<div class=\"modify__row\"><label for=\"name\">이름</label><span class=\"joinform__required-mark\">*</span></div>";
		//html+="<div class=\"modify__row\"><input type=\"text\" class=\"modify_input\" name=\"name\" id=\"name\" value= \""+memberDTO.getName()+"\"required></div>";
		html+="</li>";
		
		
		
		html+="    <li>";
		html+="      <div class=\"modify__row\"><label for=\"email\">연락가능 이메일</label><span class=\"joinform__required-mark\">*</span></div>";
		html+="      <div class=\"modify__row\"><input type=\"email\" class=\"modify_input\" name='email' id='email' value= "+memberDTO.email+" \" required></div>";
		html+="    </li>";
		
		
		
		html+="    <li><label for=\"nickname\">별칭</label></li>";
		html+="  <li><input type=\"text\" name='nickname' id='nickname' value = "+memberDTO.nickname+"/></li>";

		
		html+="<li><label for=\"birth\">생년월일</label></li>";
		html+="<li><input type=\"date\" id='birth' name='birth' value = "+memberDTO.birth+" \"/></li>	";
		
		
		html+="<li><label for=\"tel\">전화번호</label></li>";
		html+="<li><input type=\"tel\" name=\"tel\" id='tel' value="+memberDTO.tel+" \"/></li>";
		
		
		html+="<li>";
		html+="<div class=\"modify__row\"><label for=\"address\">주소</label><span class=\"joinform__required-mark\">*</span></div>";
		html+="<div class=\"modify__row\"><input type=\"text\" class=\"modify_input\" name='address' id='address'  value="+memberDTO.address+" required></div>";


		html+="</li>";
		html+="<li><input type=\"button\" id=\"modifyBtn\"></li>";
		
		
		html+="</ul>";
		html+="</form >";
		html+="</div>";
}; 	
