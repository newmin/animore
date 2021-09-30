/**
 * 
 */
 /*마이페이지 좌측 메뉴, 해당 페이지 색상으로 표시*/
 const $sideMenu = document.querySelectorAll('.mypage_aside_menu')
 $sideMenu.forEach(ele=>ele.addEventListener('click',e=>{
	document.querySelectorAll('.mypage_aside_menu').forEach(elem=>elem.classList.remove('mypage_aside_menu_highlight'))
	e.target.closest('li').classList.add('mypage_aside_menu_highlight');
}))
 
 //내가 쓴 리뷰
const myReview =  document.querySelector('.mypage__myReviewBtn')
myReview.addEventListener('click',review);
function review(){
  const URL = `/mypage/myreview`;
													 	
	request.get(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == "00"){
							//성공로직처리
							const data = res.data;
							//리뷰목록갱신
						  let html= '';
						  html += `<h2 class="mypage_content_title">내가 쓴 리뷰</h2>`;
						  html += `<hr>`;
						  html += `<table class="mypage__table">`;
						  html += `<tr>`;
						  html += `  <th class="mypage__cell mypage__th mypage__num">번호</span>`;
						  html += `  <th class="mypage__cell mypage__th myreview__bname">업체명</span>`;
						  html += `  <th class="mypage__cell mypage__th myreview__content">리뷰내용</span>`;
						  html += `  <th class="mypage__cell mypage__th mypage__score">내평점</span>`;
						  html += `  <th class="mypage__cell mypage__th mypage__date">작성일</span>`;
						  html += `</tr>`;
						  data.forEach(review => {
						    html += `<tr>`;
						    html += `    <td class="mypage__cell mypage__td">${review.rownum}</span>`;
						    html += `    <td class="mypage__cell mypage__td"><a href="/inquire/${review.bnum}">${review.bname}</a></span>`;
						    html += `    <td class="mypage__cell mypage__td"><a href="/inquire/${review.bnum}">${review.rcontent}</a></span>`;
						    html += `    <td class="mypage__cell mypage__td"><i class="fas fa-star busi-list__star"></i>${review.rscore}점</span>`;
						    html += `    <td class="mypage__cell mypage__td">${review.rvcdate}</span>`;
						    html += `</tr>`;
						  });
							html+=`</table>`;
  
  						document.querySelector('.mypage_content_container').innerHTML = html;
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

//내가 쓴 글
const $mypostBtn = document.querySelector('.mypage__mypostBtn');
let $contents = document.querySelector('.mypage_content_container');

$mypostBtn.addEventListener('click',e=>{
e.preventDefault();
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
          html += `      <th class="mypage__cell mypage__th mypage__num">번호</th> `;
          html += `      <th class="mypage__cell mypage__th mypage__bcategory">카테고리</th> `;
          html += `      <th class="mypage__cell mypage__th mypage__btitle">제목</th> `;
          html += `      <th class="mypage__cell mypage__th mypage__date">작성일</th> `;
          html += `      <th class="mypage__cell mypage__th mypage__count">조회수</th> `;
          html += `    </tr> `;
          data.forEach(post =>{
              html += `<tr class="mypost__container"> `;
              html += `    <td class="mypage__cell mypage__td mypost__num">${post.rownum}</td> `;
              html += `    <td class="mypage__cell mypage__td mypost__bcategory ">${post.bcategory}</td> `;
              html += `    <td class="mypage__cell mypage__td mypost__btitle" ><a href="/board/post/${post.bnum}" >${post.btitle}</a></td> `;
              html += `    <td class="mypage__cell mypage__td mypost__udate" >${post.bcdate}</td> `;
              html += `    <td class="mypage__cell mypage__td mypost__bhit" >${post.bhit}</td> `;
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
const $mypageReplyMenu = document.querySelector('.mypage__myReplyBtn');
	$mypageReplyMenu.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mypageReply`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == "00"){
			//성공로직처리
			console.log(res);
			const data = res.data;
			let html=``;
			html+=`<h3 class="mypage_content_title">내가 쓴 댓글</h3>`;
			html+=`<hr>`;
			html+=`<div class="mypage_content">`;
			html+=`  <table class="reply__table"> `;
			html+=`    <tr>`;
			html+=`      <th class="mypage__cell mypage__th mypage__num">번호</th>`;
			html+=`      <th class="mypage__cell mypage__th myreply__content">댓글내용</th>`;
			html+=`      <th class="mypage__cell mypage__th mypage__date">작성일</th>`;
			html+=`      <th class="mypage__cell mypage__th mypage__count">좋아요</th>`;
			html+=`    </tr>`;
			data.forEach(rec=>{
				html+=`    <tr>`
				html+=`      <td class="mypage__cell mypage__td ">${rec.rownum}</td>`
				html+=`      <td class="mypage__cell mypage__td "><a href="/board/post/${rec.bnum}">${rec.rcontent}</a></td>`
				html+=`      <td class="mypage__cell mypage__td ">${rec.rcdate}</td>`
				html+=`      <td class="mypage__cell mypage__td ">${rec.bgood}</td>`
				html+=`    </tr>`
			});
			html+=`  </table>`
			html+=`</div>`
			document.querySelector('.mypage_content_container').innerHTML = html;
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
const $goodBoardList = document.querySelector('.mypage__myGoodBtn');
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
			let html = ``;
			html+=`<h3 class='mypage_content_title'>좋아요</h3>`;
			html+=`<hr>`;
			html+=`<div class='mypage_content_container'>`;
			html+=`  <table class='reply__table'> `;
			html+=`    <tr>`;
			html+=`      <th class='mypage__cell mypage__th mypage__num'>번호</th>`;
			html+=`      <th class='mypage__cell mypage__th mypage__bcategory'>카테고리</th>`;
			html+=`      <th class='mypage__cell mypage__th mypage__btitle'>제목</th>`;
			html+=`      <th class='mypage__cell mypage__th mypage__count'>좋아요</th>`;
			html+=`    </tr>`;
			data.forEach(post=>{
				html+=`    <tr>`;
				html+=`      <td class='mypage__cell mypage__td '>${post.rownum}</td>`;
				html+=`      <td class='mypage__cell mypage__td mypost__bcategory'>${post.bcategory}</td>`;
				html+=`      <td class='mypage__cell mypage__td '><a href="/board/post/${post.bnum}">${post.btitle}</a></td>`;
				html+=`      <td class='mypage__cell mypage__td '>${post.bgood}</td>`;
				html+=`    </tr>`;
			});
			html+=`  </table>`;
			html+=`</div>`;
			document.querySelector('.mypage_content_container').innerHTML = html;
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

//개인정보수정 화면
	const $mypageModify = document.querySelector('.mypage__myInfoBtn');
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
				refreshModi(data);				//개인정보수정 리프레쉬
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

const modifyBtn_f = e =>{
	e.preventDefault();
	
	const $id = id.value;
	const $pwChk = pwChk.value;
	// const $name = name.value;
	const $name = document.querySelector('input[name="name"]').value;
	const $tel = tel.value;
	const $tel2 = document.querySelector('input[name="tel2"]').value;
	const $tel3 = document.querySelector('input[name="tel3"]').value;
	const $nickname = nickname.value;
	const $address = address.value;
	const $birth = birth.value;
	const $email = email.value;
	
	const URL = `/mypage/mypageModify`;
	const data = {
								 "id":$id,
								 "name":$name,
								 "tel":$tel,
								 "tel2":$tel2,
								 "tel3":$tel3,
								 "email":$email,
								 "nickname":$nickname,
								 "address": $address,
								 "birth" : $birth,
								 "pwChk":$pwChk
							 };
	
	request.patch(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				const data = res.data;
				refreshModi(data);				//개인정보수정 리프레쉬
				alert('회원정보가 수정되었습니다.');
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			alert(err.message);
		});
};

function refreshModi(memberDTO){
		let html = ``;
		html+=`<h2 class='mypage_content_title'>개인 정보 수정</h2>`;
		html+=`	<hr>`;
		html+=`	<form class="main" action="/mypage/mypageModify" method="post" ><input type="hidden" name ="_method" value="patch">`;
		html+=`		<ul class="joinform">`;
		html+=`			<li><div class="joinform__row"><label for="id">아이디</label></div>`;
		html+=`				  <div class="joinform__row"><input type="text"  class="joinform__input" id ='id' name ='id' value=${memberDTO.id} readonly="readonly"/></li></div>`;
		html+=`			<li>`;
		html+=`				<div class="joinform__row"><label for="name">이름</label></div>`;
		html+=`				<div class="joinform__row"><input type="text" class="joinform__input" name='name' id='name' value=${memberDTO.name} required></div>`;
		html+=`			</li>`;
		html+=`	    <li>`;
		html+=`	      <div class="joinform__row"><label for="email">연락가능 이메일</label></div>`;
		html+=`	      <div class="joinform__row"><input type="email" class="joinform__input" name='email' id='email' value=${memberDTO.email} required></div>`;
		html+=`	    </li>`;
		html+=`	    <li><div class="joinform__row"><label for="nickname">별명</label></div>`;
		html+=`		  	  <div class="joinform__row"><input type="text" class="joinform__input" name='nickname' id='nickname' value=${memberDTO.nickname}></li></div>`;
		html+=`			<li><div class="joinform__row"><label for="birth">생년월일</label></div>`;
		html+=`					<div class="joinform__row"><input type="date" class="joinform__input" id='birth' name='birth' value=${memberDTO.birth}></li></div>`;
		html+=`			<li><div class="joinform__row"><label for="tel">연락처</label></div>`;
		html+=`				<div class="joinform__row"><input type="text" class="joinform__input joinform__input--sm" name="tel" id='tel' value=${memberDTO.tel}>`;
		html+=`				<input type="text" class="joinform__input joinform__input--sm" name="tel2" id='tel2' value=${memberDTO.tel2} required>`;
		html+=`				<input type="text" class="joinform__input joinform__input--sm" name="tel3" id='tel3' value=${memberDTO.tel3} required></div>`;
		html+=`			</li>`;
		html+=`			<li>`;
		html+=`				<div class="joinform__row"><label for="address">주소</label></div>`;
		html+=`				<div class="joinform__row"><input type="text" class="joinform__input" name='address' id='address' value=${memberDTO.address} required></div>`;
		html+=`			</li>`;
		html+=`			<li><div class="joinform__row"><label for="pwChk">비밀번호 확인</label></div>`;
		html+=`					<div class="joinform__row"><input type="password" class="joinform__input" name="pwChk" id ='pwChk'/></li></div>`;
		html+=`			<li><input type="button" value="회원수정" class="modifyBtn" id="modifyBtn"></li>`;
		html+=`		</ul>`;
		html+=`	</form >`;
		document.querySelector('.mypage_content_container').innerHTML = html;
		
		const $modifyBtn = document.querySelector('#modifyBtn');
		$modifyBtn.addEventListener("click", modifyBtn_f); 
}; 	

//회원탈퇴 양식
const $mypageDelMenu = document.querySelector('.mypage__outBtn');
	$mypageDelMenu.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mypageDel`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			let html = ``;
			html+=`<h3 class='mypage_content_title'>회원탈퇴</h3>`;
			html+=`<hr>`;
			html+=`<div class='mypage_content'>`;
			html+=`  <form action='/mypage/mypageDel' method='post' class='findId'><input type='hidden' name='_method' value='delete\'>`;
			html+=`    <h1 class='findId__title'></h1>`;
			html+=`    <p class='login__errormsg' th:errors='*{global}'></p>`;
			html+=`    <div class='findId__form'>`;
			html+=`      <span class='findId__text'>비밀번호 확인</span>`;
			html+=`      <input class='findId__input' type='password' name='pwChk'>`;
			html+=`    </div>`;
			html+=`    <button class='findId__btn' type='submit'>회원탈퇴</button>`;
			html+=`  </form>`;
			html+=`</div>`;
			document.querySelector('.mypage_content_container').innerHTML = html;
			document.querySelector('.findId__btn').addEventListener('click',outMember);
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});
});

//회원탈퇴 처리
const outMember = e => {
	e.preventDefault();
	
	if(!confirm('회원탈퇴 후에는 기록을 되돌릴 수 없습니다. 정말로 떠나시겠어요?')){
		return;
	}
	
	const pwChk = document.querySelector('.findId__input').value;
	
	const URL = `/mypage/mypageDel`;
	const data = {
							 "pwChk":pwChk
						 };
	
	request.patch(URL,data)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == "00"){
							//성공로직처리
							const data = res.data;
							alert(res.rtmsg);
							//홈으로 이동
							location.href="/";
					}else{
						alert(res.rtmsg);
						throw new Error(res.rtmsg);
					}
			})
			.catch(err=>{
					//오류로직 처리
					console.log(err.message);
			});
};

const outMemberPage = e =>{

}

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
			//document.querySelector('.mypage_content_container').innerHTML = data;
			refreshPwchange(data);
			
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
	console.log('수정처리');

	const $pw = document.getElementById('pw');
	const $pwChk =document.getElementById('pwChk');
	const $pwChk2 =document.getElementById('pwChk2');
	
	
	
	const URL = `/mypage/mypagePwModify`;
	const data = {
							
								 "pw":$pw.value,
								 "pwChk":$pwChk.value,
								 "pwChk2":$pwChk2.value
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
const $mybusilist = document.querySelector('.mypage__mybusilist');

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
			let html =``;
			html+=`<h3 class='mypage_content_title'>내업체 목록</h3>`;
			html+=`<hr>`;
			html+=`<div class='mypage_content_container'>`;
			html+=`<table class='reply__table'> `;
			html+=`<tr class="w3-hover-green">`;
			html+=`<th class="mypage__cell mypage__th mypage__num">번호</th>`;
			html+=`<th class="mypage__cell mypage__th mybusilist___bname">업체명</th>`;
			html+=`<th class="mypage__cell mypage__th mybusilist__baddress">주소</th>`;
			html+=`<th class="mypage__cell mypage__th mybusilist__btel">전화번호</th>`;
			html+=`<th class="mypage__cell mypage__th mybusilist__btn">수정</th>`;
			html+=` <th></th>`;
			html+=` </tr>`;
			data.forEach(busi=>{
			html+=`<tr class="w3-hover-green">`;
			html+=`<td class='mypage__cell mypage__td'><span>${busi.rownum}</span></td>`;
			html+=`<td class='mypage__cell mypage__td mybusilist___bname'><a href="/inquire/${busi.bnum}">${busi.bname}</a></td>`;
			html+=`<td class='mypage__cell mypage__td mybusilist__baddress'>${busi.baddress}</td>`;
			html+=`<td class='mypage__cell mypage__td mybusilist__btel'>${busi.btel}</td>`;
			html+=`<td class='mypage__cell mypage__td mybusilist__btn' type='button' class='busimodifyBtn'><a href='/mypage/mybusiModify/${busi.bnum}'>수정</a></td>`;
			html+=`</tr>`;
			});
			html+=`</table>`;
			html+=`</div>`;
			document.querySelector('.mypage_content_container').innerHTML = html;
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
	
	html+="<h2 class=\"mypage_content_title\">비밀번호 수정</h2>";
	
	html+="<form class=\"main\" action=\"/mypage/mypagePwModify\"/ method=\"post\" \"><input type=\"hidden\" name = \"_method\" value=\"patch\">";
	
	html+="<hr>";
	
	html+="<ul>";
	html+="<li><label for=\"id\">아이디</label></li>";
	html+="<li><input type=\"text\" id ='id' name ='id' value="+memberDTO.id+" readonly='readonly'/></li>";
	html+="<li><label for=\"pw\">현재 비밀번호</label></li>";
	html+="<li><input type=\"password\" name='pw' id = 'pw' \"/></li>";
	html+="<li><label for=\"pw2\">새로운 비밀번호</label></li>";
	html+="<li><input type=\"password\" name='pwChk' id = 'pwChk' \"/></li>";
	html+="<li><label for=\"pw3\">새로운 비밀번호 확인</label></li>";
	html+="<li><input type=\"password\" name='pwChk2' id = 'pwChk2' \"/></li>";
	html+="<li><input class=\"pwModi_btn\" type=\"button\" value='비밀번호수정' id=\"changPw\"></li>";
	html+="</ul>";
	html+="</form>";
	
	document.querySelector('.mypage_content_container').innerHTML = html;
};
