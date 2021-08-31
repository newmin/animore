/**
 * 함수 하나로 쓸수있을듯
 */




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
