/**
 * 
 */
 
 //즐겨찾기 등록 / 즐겨찾기 등록/삭제 + 하트 채우기/비우기

const $favorBtn = document.querySelector('.favorBtn');

//하트 색상 함수
function heart(){
 	$favorBtn.classList.toggle('far');
  $favorBtn.classList.toggle('fas');
}
//즐겨찾기 토글 함수
function favoriteToggle(){
    $favorBtn.classList.toggle('unfavorite');
    $favorBtn.classList.toggle('favorite');
}

$favorBtn.addEventListener('click',e=>{
	console.log('즐겨찾기')	
	
	if($id == null){
		if(confirm('로그인 회원만 사용가능한 기능입니다. 로그인하시겠습니까?')){
			location.href="/login";
		}
		return;
	}
	
	const URL = `/favor/${$busi.bnum}`;
	
	/*if(!$favorBtn.classList.contains('favorite')){	*/
		request.get(URL)
				.then(res=>res.json())
				.then(res=>{
					console.log(res)
						if(res.rtcd == "00"){
								//하트 변신
								favoriteToggle();
								heart();
						}else{
								throw new Error(res.rtmsg);
								console.log('else!');
						}
				})
				.catch(err=>{
						//오류로직 처리
						console.log(err.message);
						alert(err.message);
				});
	/*} */
	
/*	else {
		request.delete(URL)
				.then(res=>res.json())
				.then(res=>{
						if(res.rtcd == '00'){
								//하트 변신
								favoriteToggle();
								heart();
								alert('즐겨찾기 해제되었습니다.')
						}else{
								throw new Error(res.rtmsg);
								console.log('else!');
						}
				})
				.catch(err=>{
						//오류로직 처리
						console.log(err.message);
							alert('두번째');
						alert(err.message);
						console.log('catch!')
				});*/
	}												 	
)